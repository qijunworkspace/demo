# Elasticsearch学习总结

## 一、基本概念

Elasticsearch的索引思路：一切设计都是为了提高搜索的性能 

> 将磁盘里的东西尽量搬进内存，减少磁盘随机读取次数(同时也利用磁盘顺序读特性)，结合各种奇技淫巧的压缩算法，用及其苛刻的态度使用内存。
>
> Lucene主要负责编写和维护索引文件，Elasticsearch则是在Lucene的基础之上维护元数据信息，比如Mapping和集群状态等。



**使用Elasticsearch注意**

- 不需要索引的字段，一定要明确定义出来，因为默认是自动建索引的
- 同样的道理，对于String类型的字段，不需要analysis的也需要明确定义出来，因为默认也是会analysis的
- 选择有规律的ID很重要，随机性太大的ID(比如java的UUID)不利于查询



**倒排索引**

对于文档中的单个字段建立倒排索引：Term (排序的Term Dictionary + 内存中Term Index)  -> Posting List（要求有序，进行压缩）。

多字段索引：利用跳表（Skip-list）的数据结构做快速“与”运算，或者当posting list 按照bitmap存储时使用bitset的按位与操作。



**段 Segment**

索引是由段（Segment）组成的，段存储在硬盘（Disk）文件中，段不是实时更新的，段在写入磁盘后，就不再被更新。

Elasticsearch引擎把被删除的文档的信息存储在一个单独的文件中，在搜索数据时，Elasticsearch引擎首先从段中查询，再从查询结果中过滤被删除的文档，这意味着，段中存储着“被删除”的文档，这使得段中含有”正常文档“的密度降低。多个段可以通过段合并（Segment Merge）操作把“已删除”的文档将从段中物理删除，把未删除的文档合并到一个新段中，新段中没有”已删除文档“，因此，段合并操作能够提高索引的查找速度，但段合并是IO密集型的操作，需要消耗大量的硬盘IO。



**写入数据**

+ 客户端选择一个node发送请求过去，这个node就是coordinating node (协调节点)
+ coordinating node对document进行路由，将请求转发给对应的node
+ 实际上的node上的primary shard处理请求，然后将数据同步到replica node
+ coordinating node发现primary node和所有的replica node都完成，就会返回请求到客户端



**读取数据**

读取某一条数据时，根据document大的全局唯一ID进行Hash，路由到对应的primary shard上面去

+ 客户端发送任何一个请求到任意一个node，成为coordinate node
+ coordinate node 对document进行路由，将请求转发到对应的node，此时会使用round-robin随机轮询算法，在primary shard 以及所有的replica中随机选择一个，让读请求负载均衡
+ 接受请求的node，返回document给coordinate note
+ coordinate node返回给客户端



**搜索数据**

+ 客户端发送一个请求给coordinate node
+ 协调节点将搜索的请求转发给所有的shard对应的primary shard 或replica shard
+ Query Phase：每一个shard 将自己搜索的结果（其实也就是一些唯一标识），返回给协调节点，由协调节点进行数据的合并，排序，分页等操作，产出最后的结果
+ Fetch Phase ，接着由协调节点，根据唯一标识去各个节点进行拉去数据，最总返回给客户端



## 二、映射配置

**字段数据类型**

在文档类型的properties属性中，定义字段的type属性，指定字段的数据类型

- **字符串类型**：string；
- **数值类型**：字节（byte）、2字节（short）、4字节（integer）、8字节（long）、float、double；
- **布尔类型**：boolean，值是true或false；
- **时间/日期类型**：date，用于存储日期和时间；
- **二进制类型**：binary；
- **IP地址类型**：ip，以字符串形式存储IPv4地址；
- **特殊数据类型**：token_count，用于存储索引的字数信息



**字段的通用属性**

+ **index**：该属性控制字段是否编入索引被搜索，该属性共有三个有效值：analyzed、no和not_analyzed：
  + analyzed：表示该字段被分析，编入索引，产生的token能被搜索到；
  + not_analyzed：表示该字段不会被分析，使用原始值编入索引，在索引中作为单个词；
  + no：不编入索引，无法搜索该字段；
  + 其中analyzed是分析，分解的意思，默认值是analyzed，表示将该字段编入索引，以供搜索。

+ **store**：指定是否将字段的原始值存储到倒排索引，默认值是false，字段值被分析，能够被搜索，但是，字段原始值不会存储。默认情况下，该字段的值会被存储到\_source字段中，如果想要获取单个或多个字段的值，而不是整个\_source字段，可以使用 source filtering来实现；

  > 映射参数index和store的区别在于：
  >
  > - **store**用于获取（Retrieve）字段的原始值，不支持查询，可以使用投影参数fields，对store属性为true的字段进行过滤，只获取（Retrieve）特定的字段，减少网络负载；
  > - **index**用于查询（Search）字段，当index为analyzed时，对字段的分词执行全文查询；当index为not_analyzed时，字段的原始值作为一个分词，只能对字段的原始文本执行词条查询；

+ **boost**：默认值是1，定义了字段在文档中的重要性/权重；

+ **include_in_all**：该属性指定当前字段是否包括在\_all字段中，默认值是ture，所有的字段都会包含\_all字段中；如果index=no，那么属性include_in_all无效，这意味着当前字段无法包含在_all字段中；

+ **copy_to**：该属性指定一个字段名称，Elasticsearch引擎将当前字段的值复制到该属性指定的字段中；

+ **doc_values**：**文档值**是存储在**硬盘上**的索引时（indexing time）数据结构，对于not_analyzed字段，默认值是true，analyzed string字段不支持文档值。

  > 为true时指定将字段的值写入到**硬盘上**的列式结构，实现了单个字段的数据访问模式，能够高效执行排序和聚合搜索。文档值数据存在硬盘上，在**文档索引时**创建，存储的数据和字段存储在_source 字段的数据相同，文档值支持所有的字段类型，除了analyzed string 字段之外。
  >
  > 如果不需要对字段执行排序或聚合操作，可以禁用字段的文档值，以节省硬盘空间

+ **fielddata(顺排索引)**：字段数据是存储在**内存中**的查询时（querying time）数据结构，只支持analyzed string字段。该数据结构在字段第一次执行聚合，排序或被脚本访问时创建。比doc_value的查询性能更高。

  > 创建过程：在读取整个倒排索引（Inverted Index）时，Elasticsearch从硬盘上加载倒排索引的每个段（Segment），倒转其中词（Term）和文档的关系，创建**文档和词条**之间的关系，即创建顺排索引，并将其存储在JVM堆内存中。加载字段数据的过程是非常消耗IO资源的，一旦被加载，就被存储在内存中，直到段的生命周期结束。
  >
  > fielddata是延迟加载的，在加载的时候是这个字段所有的值都要加载
  >
  > elasticsearch.yml： indices.fielddata.cache.size: 20%，超出限制，清除内存已有的fielddata。

+ **null_value**：该属性指定一个值，当字段的值为NULL时，该字段使用null_value代替NULL值；Elasticsearch中NULL值不能被索引和搜索，当一个字段设置为NULL值，Elasticsearch引擎认为该字段没有任何值，使用该属性为NULL字段设置一个指定的值，使该字段能够被索引和搜索。

+ **fields属性**：多元字段（multi-fields），用不同的处理方式，把一个相同的字段编入索引，以实现不同的目的。



**字符串类型特定属性**

+ **analyzer**：该属性定义用于建立索引和搜索的分析器名称，默认值是全局定义的分析器名称，该属性可以引用在配置结点（settings）中自定义的分析器；
+ **search_analyzer**：该属性定义的分析器，用于处理特定字段的查询字符串；
+ **ignore_above**：该属性指定一个整数值，当字符串字段（analyzed string field）的字节数量大于该数值之后，超过长度的部分字符数据将不能被analyzer处理，不能被编入索引；对于 not analyzed string字段，超过长度的部分字符将被忽略，不会被编入索引。默认值是0，禁用该属性；
+ **position_increment_gap**：该属性指定在相同词的位置上增加的gap，默认值是100，以防止短语匹配或位置匹配查询出现跨越多个词的异常；
+ **index_options**：索引选项控制添加到倒排索引（Inverted Index）的信息，这些信息用于搜索（Search）和高亮显示：
  - docs：只索引文档编号(Doc Number)
  - freqs：索引文档编号和词频率（term frequency）
  - positions：索引文档编号，词频率和词位置（序号）
  - offsets：索引文档编号，词频率，词偏移量（开始和结束位置）和词位置（序号）
  - 默认情况下，被分析的字符串（analyzed string）字段使用positions，其他字段使用docs; 



**数值类型特定属性**

- **precision_step**：该属性指定为数值字段每个值生成的term数量，值越低，产生的term数量越高，范围查询越快，索引越大，默认值是4；
- **ignore_malformed**：忽略格式错误的数值，默认值是false，不忽略错误格式，对整个文档不处理，并且抛出异常；
- **coerce**：默认值是true，尝试将字符串转换为数值，如果字段类型是整数，那么将小数取整；



**日期类型特定属性**

- **format**：指定日期的格式，例如：“yyyy-MM-dd hh:mm:ss”
- **precision_step**：该属性指定数值字段每隔多少数值，生成一个词（term）；step值越低，产生的词数量越高，范围查询越快，索引越大，占用存储空间越大；
- **ignore_malformed**：忽略错误格式，默认值是false，不忽略错误格式；



**索引元字段**

+ **\_all字段**：可配置，默认情况下，\_all字段是启用的，包含了索引中所有字段的数据，然而这一字段使索引变大，如果不需要，请禁用该字段，或排除某些字段。为了在\_all字段中不包括某个特定字段，在字段中设置“include_in_all”属性为false。把其他字段的值拼接成一个大的字符串，字段值之间使用空格分隔。
+ **\_source字段**：可配置，表示在生成索引的过程中，存储发送到Elasticsearch的原始JSON文档，默认情况下，该字段会被启用，因为索引的局部更新功能依赖该字段。
+ **\_routing字段**：可配置，将一个文档值进行哈希映射，并将该文档路由到指定的分片，路由的公式是：

```
shard_num = hash(_routing) % num_primary_shards
```



**分析器（Analyzer）**的作用就是把传入Lucene的文档数据转化为倒排索引，把文本处理成可被搜索的词。分析器由一个分词器（Tokenizer）和零个或多个标记过滤器（Token Filter）组成，也可以包含零个或多个字符过滤器（Character Filter）。

+ 常见的分析器：
  + **标准分析器（Standard）**：由标准分词器（Standard Tokenizer），标准标记过滤器（Standard Token Filter），小写标记过滤器（Lower Case Token Filter）和停用词标记过滤器（Stopwords Token Filter）组成。
  + **简单分析器（Simple）**：小写标记分词器（Lower Case Tokenizer），在非字母位置上分割文本，并把分词转换为小写形式，功能上是Letter Tokenizer和 Lower Case Token Filter的结合，但是性能更高，一次性完成两个任务。
  + **空格分析器（Whitespace）**：实际上是空格分词器（Whitespace Tokenizer)。
  + **停用词分析器（Stopwords）**：由小写分词器（Lower Case Tokenizer）和停用词标记过滤器（Stop Token Filter）构成，配置参数stopwords 或 stopwords_path指定停用词列表。
  + **雪球分析器（Snowball）**：由标准分词器（Standard Tokenizer），标准过滤器（Standard Filter），小写过滤器（Lowercase Filter），停用词过滤器（Stop Filter）和雪球过滤器（Snowball Filter）构成。参数language用于指定语言。
  + **自定义分析器（Custom）**：用户定制分析器。参数tokenizer 用于指定分词器，filter用于指定标记过滤器，char_filter用于指定字符过滤器。

+ **字符过滤器**用于从文档的原始文本去除部分字符，有映射字符过滤器（Mapping Char Filter）、HTML标记字符过滤器（HTML Strip Char Filter）和模式替换字符过滤器（Pattern Replace Char Filter）。

+ **分词器（tokenizer）**在字符过滤器之后工作，用于把文本分割成多个标记（Token），一个标记基本上是词加上一些额外信息，分词器的处理结果是标记流，准备被过滤器处理。

+ **标记过滤器**在分词器之后工作，用来处理标记流中的标记。标记过滤从分词器中接收标记流，能够删除标记，转换标记，或添加标记。，常用的过滤器是:
  + Stemmer 词干过滤器
  + Lowercase 小写标记过滤器
  + Stopwords 停用词标记过滤器
  + Synonym 同义词过滤器



**复合数据类型**

指数组类型，对象类型和嵌套类型，各个类型的特点分别是：

- 数组字段是指一个字段有多个值，每个值都是该数组字段的一个元素；元素的类型可以是基础类型，也可以是文档类型；在同一个数组中，数组元素的数据类型是相同的，不支持元素为多个数据类型；
- 对象类型是指字段的值是一个JSON文档；
- 嵌套字段是指对象类型的一个特殊版本，Elasticsearch引擎在内部把嵌套字段索引成单个文档。如果在嵌套字段中定义对象数组，那么对象数组中的每个元素（文档）都被索引成单个文档，每个文档都能被独立地查询。



## 三、搜索查询

