# HBASE 知识点

## 一、基本概念

HBase的表能够作为MapReduce任务的输入和输出，可以通过Java API来存取数据，也可以通过REST、Avro或者Thrift的API来访问。HBase为了解决海量数据的扩展性，支持简单的增加节点来实现线性扩展，从而在集群上管理海量的非结构化或半结构化的稀疏数据。HBase仅能通过主键（row key）或主键的range检索数据，支持单行事务。

优点在于可以实现高性能的**并发**读写操作，同时Hbase还会对数据进行透明的切分，这样就使得存储本身具有了水平**伸缩性**。

| 列式存储：                                   |
| -------------------------------------------- |
| 基于一列或比较少的列计算的时候               |
| 经常关注一张表某几列而非整表数据的时候       |
| 数据表拥有非常多的列的时候                   |
| 数据表有非常多行数据并且需要聚集运算的时候   |
| 数据表列里有非常多的重复数据，有利于高度压缩 |

| 行式存储：                           |
| ------------------------------------ |
| 关注整张表内容，或者需要经常更新数据 |
| 需要经常读取整行数据                 |
| 不需要聚集运算，或者快速查询需求     |
| 数据表本身数据行并不多               |
| 数据表的列本身有太多唯一性的数据     |



###  **1 架构**

Apache HBase是运行在Hadoop集群上的数据库。

![hbase architecture](/img/hbase_architecture.png)



物理上来说，HBase是由三种类型的服务器(**Region server**，**HBase HMaster** ，**ZooKeeper**)以主从模式构成的。

+ **HBase HMaster**为RegionServer分配Region，负责RegionServer的负载均衡，处理schema更新请求。Client访问hbase上数据的过程并不需要Master参与（寻址访问Zookeeper和RegionServer，数据读写访问RegionServer），Master仅仅维护Table和Region的元数据信息，负载很低。

+ 集群中负责管理Region的结点叫做**Region server**。Region server负责数据的读写。每一个Region server大约可以管理1000个region。

+ **ZooKeeper**作为HDFS的一部分，负责维护集群的状态（某台服务器是否在线，服务器之间数据的同步操作及master的选举等）。

Hadoop **DataNode**负责存储所有Region Server所管理的数据。HBase中的所有数据都是以HDFS文件的形式存储的。出于使Region server所管理的数据更加本地化的考虑，Region server是根据HDFS DataNode分布的。**NameNode**负责维护构成文件的所有物理数据块的元信息（metadata）。

![hbase architecture](/img/hbase_architecture2.png)





**Region server组成图**

![Region server](/img/region_server.png)

### 2 **物理存储**

对于HBase中每一个cell的值，其完整的索引应当是**Table::RowKey::Column family::Column::Timestamp --> Value **。Hbase的表是稀疏的。如果某一列没有数据，则其不会被存储。表中的cell有其对应的连续改变的version。version默认参考timestamp，数据库中可能存储了多个不同version的值（默认3个）。Get操作根据给定的参数返回特定version的数据，默认情况下最新版本的数据将会被返回。

以列族为组织方式组织数据存储，随着数据不断插入表，region不断增大，当region的某个列族(store)达到一个阈值(默认256M)，会拆分成两个region。

hfile是每个列族的存储数据文件，多个存储列族的hfile在以rowkey为逻辑单位的行关系上逻辑组成了一个个的region，region被分配到多个region server机器上来存储。

HBase中的所有数据文件都存储在Hadoop HDFS文件系统上，主要包括上述提出的两种文件类型：

1. HFile， HBase中KeyValue数据的存储格式，HFile是Hadoop的二进制格式文件，实际上StoreFile就是对HFile做了轻量级包装，即StoreFile底层就是HFile 

2. HLogFile，HBase中WAL（Write Ahead Log） 的存储格式，物理上是Hadoop的Sequence File

### 3 数据模型

![数据模型](/img/hbase_model.png)

+ **行键（Row Key）**：表的主键，表中的记录默认按照行键升序排序  

+ **时间戳（Timestamp）**：每次数据操作对应的时间戳，可以看作是数据的版本号

+ **列族（Column  Family）**：表在水平方向有一个或者多个列族组成，一个列族中可以由任意多个列组成，列族支持动态扩展，无需预先定义列的数量以及类型，所有列均以二进制格式存储，用户需要自行进行类型转换。所有的列族成员的前缀是相同的，例如“abc:a1”和“abc:a2”两个列都属于abc这个列族。

+ **表和区域（Table&Region）**：当表随着记录数不断增加而变大后，会逐渐分裂成多份，成为区域，一个区域是对表的水平划分，不同的区域会被Master分配给相应的RegionServer进行管理

+ **单元格（Cell）**：表存储数据的单元。由{行健，列（列族:标签），时间戳}唯一确定，其中的数据是没有类型的，以二进制的形式存储。





## 二、操作

**写入操作**

+ HBase的用户发出一个`PUT`请求时（也就是HBase的写请求），HBase进行处理的第一步是将数据写入HBase的write-ahead log（WAL）中。
+ 当数据被成功写入WAL后，HBase将数据存入MemStore。这时HBase就会通知用户PUT操作已经成功了。
  + Memstore存在于内存中，其中存储的是按键排好序的待写入硬盘的数据。数据也是按键排好序写入HFile中的。每一个Region中的每一个Column family对应一个Memstore文件。因此对数据的更新也是对应于每一个Column family。
  + 当MemStore中积累了足够多的数据之后，整个Memcache中的数据会被一次性写入到HDFS里的一个新的HFile中。因此HDFS中一个Column family可能对应多个HFile。这个HFile中包含了相应的cell，或者说键值的实例。这些文件随着MemStore中积累的对数据的操作被flush到硬盘上而创建。
  + HBase中的键值数据对存储在HFile中。因为MemStore中的数据已经按照键排好序，所以这是一个顺序写的过程。由于顺序写操作避免了磁盘大量寻址的过程，所以这一操作非常高效。
  + HFile中包含了一个多层索引系统。这个多层索引是的HBase可以在不读取整个文件的情况下查找数据。这一多层索引类似于一个B+树。HFile的索引在HFile被打开时会被读取到内存中。这样就可以保证数据检索只需一次硬盘查询操作。

 **读合并（Read Merge）、读放大（Read amplification）**

HBase中对应于某一行数据的cell可能位于多个不同的文件或存储介质中。比如已经存入硬盘的行位于硬盘上的HFile中，新加入或更新的数据位于内存中的MemStore中，最近读取过的数据则位于内存中的Block cache中。所以当我们读取某一行的时候，为了返回相应的行数据，HBase需要根据Block cache，MemStore以及硬盘上的HFile中的数据进行所谓的读合并操作。

+ HBase会首先从Block cache（HBase的读缓存）中寻找所需的数据。

+ 接下来，HBase会从MemStore中寻找数据。因为作为HBase的写缓存，MemStore中包含了最新版本的数据。

+ 如果HBase从Block cache和MemStore中没有找到行所对应的cell所有的数据，系统会接着根据索引和`bloom filter`从相应的HFile中读取目标行的cell的数据。

![read](/img/hbase_read.png)

**读放大效应（Read amplification）**: 一个MemStore对应的数据可能存储于多个不同的HFile中（由于多次的flush），因此在进行读操作的时候，HBase可能需要读取多个HFile来获取想要的数据。这会影响HBase的性能表现。



**数据合并 Compaction**

Minor Compaction：自动选取一些较小的HFile进行合并，通过Merge sort的形式合并为较大的文件，从而减少了存储的HFile的数量，提升HBase的性能。

Major Compaction：HBase将对应于某一个Column family的所有HFile重新整理并合并为一个HFile，并在这一过程中删除已经删除或过期的cell，更新现有cell的值。这一操作大大提升读的效率。这一过程包含大量的硬盘I/O操作以及网络数据通信。这一过程也称为写放大（Write amplification）。在Major compaction进行的过程中，当前Region基本是处于不可访问的状态。Major compaction会将任何不在本地的数据（Region server管理存储在远端服务器上的region）下载至本地。



**Region Split**

+ HBase中的表格可以根据行键水平分割为一个或几个region。每个region中包含了一段处于某一起始键值和终止键值之间的连续的行键。

+ 每一个region的默认大小为1GB。

+ 相应的Region server负责向客户提供访问某一region中的数据的服务。

+ 每一个Region server能够管理大约1000个region（这些region可能来自同一个表格，也可能来自不同的表格）。

每一个表格最初都对应于一个region。随着region中数据量的增加，region会被分割成两个子region。每一个子region中存储原来一半的数据。同时Region server会通知HMaster这一分割。出于负载均衡的原因，HMaster可能会将新产生的region分配给其他的Region server管理







