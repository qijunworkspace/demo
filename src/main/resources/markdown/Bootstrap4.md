# BootStrap4 知识点

#### 容器类

---

Bootstrap 4 需要一个容器元素来包裹网站的内容。

我们可以使用以下两个容器类：

- .container 类用于固定宽度并支持响应式布局的容器。
- .container-fluid 类用于 100% 宽度，占据全部视口（viewport）的容器



#### 网格系统

---

Bootstrap 提供了一套响应式、移动设备优先的流式网格系统，随着屏幕或视口（viewport）尺寸的增加，系统会自动分为最多 12 列。

Bootstrap 4 网格系统有以下 5 个类:

- .col- 针对所有设备
- .col-sm- 平板 - 屏幕宽度等于或大于 576px
- .col-md- 桌面显示器 - 屏幕宽度等于或大于 768px
- .col-lg- 大桌面显示器 - 屏幕宽度等于或大于 992px
- .col-xl- 超大桌面显示器 - 屏幕宽度等于或大于 1200px

Bootstrap4 网格系统规则:

- 网格每一行需要放在设置了 `.container` (固定宽度) 或 `.container-fluid` (全屏宽度) 类的容器中，这样就可以自动设置一些外边距与内边距。
- 使用行来创建水平的列组。
- 内容需要放置在列中，并且只有列可以是行的直接子节点。
- 预定义的类如 **.row** 和 **.col-sm-4** 可用于快速制作网格布局。
- 列通过填充创建列内容之间的间隙。 这个间隙是通过 **.rows** 类上的负边距设置第一行和最后一列的偏移。
- **网格列是通过跨越指定的 12 个列来创建**。 例如，设置三个相等的列，需要使用用三个**.col-sm-4** 来设置。
- Bootstrap 3 和 Bootstrap 4 最大的区别在于 Bootstrap 4 现在使用 flexbox（弹性盒子） 而不是浮动。 Flexbox 的一大优势是，没有指定宽度的网格列将自动设置为**等宽与等高列** 。
- 偏移列通过 **offset-\*-\*** 类来设置。第一个星号( * )可以是 **sm、md、lg、xl**，表示屏幕设备类型，第二个星号( * )可以是 **1** 到 **11** 的数字。



#### 文字排版

---

Bootstrap 4 默认的 **font-size** 为 16px, **line-height** 为 1.5。默认的 **font-family** 为 "Helvetica Neue", Helvetica, Arial, sans-serif。此外，所有的 **\<p>** 元素 **margin-top: 0** 、 **margin-bottom: 1rem** (16px)

+ 四个 Display 类来控制标题（更大更粗）的样式: .display-1, .display-2, .display-3, .display-4；
+  HTML **\<small>** 元素用于创建字号更小的颜色更浅的文本（副标题）；
+ 定义 **\<mark>** 为黄色背景及有一定的内边距（文本高亮）;
+ 定义 HTML **\<abbr>** 元素的样式为显示在文本底部的一条虚线边框（title提示）；
+ 引用的内容可以在 **\<blockquote>** 上添加 **.blockquote** 类（引用文字及来源）；
+ 定义 HTML **\<dl>** 元素的样式为引用列表，结合**\<dt>**、**\<dd>**显示每一项的标题及描述；
+ 定义 HTML **\<code>** 元素样式，显示引用的代码（浅红色）；
+ 定义 HTML **\<kbd>** 元素样式，显示键盘按键组合（黑底白字）；
+  定义 HTML **\<pre>** 元素的样式，显示多行代码样式（保留空格和换行符）；



+ | 样式类名                           | 描述                                                         |
  | ---------------------------------- | ------------------------------------------------------------ |
  | **.font-weight-bold/normal/light** | 加粗、正常、细化文本                                         |
  | **.font-italic**                   | 斜体文本                                                     |
  | **.lead**                          | 突出段落                                                     |
  | **.small**                         | 指定更小文本 (为父元素的 85% )                               |
  | **.text-left/center/right**        | 文字居左、居中、居右                                         |
  | **.text-justify**                  | 设定文本对齐,段落中超出屏幕部分文字自动换行                  |
  | **.text-nowrap**                   | 段落中超出屏幕部分不换行                                     |
  | **.text-lowercase/uppercase**      | 设定文本小写、大写                                           |
  | **.text-capitalize**               | 设定单词首字母大写                                           |
  | **.list-unstyled**                 | 移除默认的列表样式，列表项中左对齐 ( <ul> 和 <ol> 中)        |
  | **.list-inline**                   | 将所有列表项放置同一行( <ul> 和 <ol> )                       |
  | **.pre-scrollable**                | 使 <pre> 元素可滚动，代码块区域最大高度为340px,一旦超出这个高度,就会在Y轴出现滚动条 |



#### 颜色

---

+ 文本颜色

  ```html
    <p class="text-muted">柔和的文本。</p>
    <p class="text-primary">重要的文本。</p>
    <p class="text-success">执行成功的文本。</p>
    <p class="text-info">代表一些提示信息的文本。</p>
    <p class="text-warning">警告文本。</p>
    <p class="text-danger">危险操作文本。</p>
    <p class="text-secondary">副标题。</p>
    <p class="text-dark">深灰色文字。</p>
    <p class="text-light">浅灰色文本（白色背景上看不清楚）。</p>
    <p class="text-white">白色文本（白色背景上看不清楚）。</p>
  ```

+ 链接颜色

  ```html
    <a href="#" class="text-muted">柔和的链接。</a>
    <a href="#" class="text-primary">主要链接。</a>
    <a href="#" class="text-success">成功链接。</a>
    <a href="#" class="text-info">信息文本链接。</a>
    <a href="#" class="text-warning">警告链接。</a>
    <a href="#" class="text-danger">危险链接。</a>
    <a href="#" class="text-secondary">副标题链接。</a>
    <a href="#" class="text-dark">深灰色链接。</a>
    <a href="#" class="text-light">浅灰色链接。</a>
  ```

+ 背景颜色

  ```html
    <p class="bg-primary text-white">重要的背景颜色。</p>
    <p class="bg-success text-white">执行成功背景颜色。</p>
    <p class="bg-info text-white">信息提示背景颜色。</p>
    <p class="bg-warning text-white">警告背景颜色</p>
    <p class="bg-danger text-white">危险背景颜色。</p>
    <p class="bg-secondary text-white">副标题背景颜色。</p>
    <p class="bg-dark text-white">深灰背景颜色。</p>
    <p class="bg-light text-dark">浅灰背景颜色。</p>
  ```

  

#### 表格

---

+ `.table` 类来设置基础表格的样式；

+ `.table-striped` 类，您将在 **\<tbody>** 内的行上看到条纹；

+ `.table-bordered` 类可以为表格添加边框；

+ `.table-hover`类可以为表格的每一行添加鼠标悬停效果（灰色背景）；

+ `.table-dark`类可以为表格添加黑色背景（黑底白字）；

+ 为每一行添加颜色类：`table-primary`、`table-success`、`table-danger`、`table-info`、`table-warning`、`table-active`、`table-secondary`、`table-light`、`table-dark`等；

+ `.table-sm` 类用于通过减少内边距来设置较小的表格（更紧凑）；

+ `.table-responsive` 类用于创建**响应式**表格：在屏幕宽度小于 992px 时会创建水平滚动条，如果可视区域宽度大于 992px 则显示不同效果（没有水平滚动条）；

  ```
  .table-responsive-sm	< 576px
  .table-responsive-md	< 768px
  .table-responsive-lg	< 992px
  .table-responsive-xl	< 1200px
  ```



#### 图像

---

+ `.rounded` 类可以让图片显示圆角效果
+ `.rounded-circle` 类可以设置椭圆形图片
+ `.img-thumbnail` 类用于设置图片缩略图(图片有边框)
+ 使用 `.float-right` 类来设置图片右对齐，使用 `.float-left` 类设置图片左对齐
+ 在 **\<img>** 标签中添加 **.img-fluid** 类来设置响应式图片。`.img-fluid`类设置了 **max-width: 100%;** 、 **height: auto;** 



#### 信息提示框

---

+ 提示框可以使用 **.alert** 类, 后面加上 `.alert-success, .alert-info, .alert-warning, .alert-danger, .alert-primary, .alert-secondary, .alert-light`或 `.alert-dark` 类来实现。

+ 提示框中在链接的标签上添加 `.alert-link` 类来设置匹配提示框颜色的链接

+ 在提示框中的 div 中添加 **.alert-dismissible** 类，然后在关闭按钮的链接上添加 **class="close"** 和 **data-dismiss="alert"** 类来设置提示框的关闭操作。

  ```html
  <div class="alert alert-success alert-dismissible">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <strong>成功!</strong> 指定操作成功提示信息。
  </div>
  ```

+ **.fade** 和 **.show** 类用于设置提示框在关闭时的淡出和淡入效果



#### 按钮

---

+ 按钮类可用于 **\<a>**, **\<button>**, 或 **\<input>** 元素上

  ```html
  <button type="button" class="btn">基本按钮</button>
  <button type="button" class="btn btn-primary">主要按钮</button>
  <button type="button" class="btn btn-secondary">次要按钮</button>
  <button type="button" class="btn btn-success">成功</button>
  <button type="button" class="btn btn-info">信息</button>
  <button type="button" class="btn btn-warning">警告</button>
  <button type="button" class="btn btn-danger">危险</button>
  <button type="button" class="btn btn-dark">黑色</button>
  <button type="button" class="btn btn-light">浅色</button>
  <button type="button" class="btn btn-link">链接</button>
  
  <a href="#" class="btn btn-info" role="button">链接按钮</a>
  <input type="button" class="btn btn-info" value="输入框按钮">
  ```

+ 按钮边框及文字颜色

  ```html
  <button type="button" class="btn btn-outline-primary">主要按钮</button>
  <button type="button" class="btn btn-outline-secondary">次要按钮</button>
  <button type="button" class="btn btn-outline-success">成功</button>
  <button type="button" class="btn btn-outline-info">信息</button>
  <button type="button" class="btn btn-outline-warning">警告</button>
  <button type="button" class="btn btn-outline-danger">危险</button>
  <button type="button" class="btn btn-outline-dark">黑色</button>
  <button type="button" class="btn btn-outline-light text-dark">浅色</button>
  ```

+ 按钮大小

  ```html
  <button type="button" class="btn btn-primary btn-lg">大号按钮</button>
  <button type="button" class="btn btn-primary">默认按钮</button>
  <button type="button" class="btn btn-primary btn-sm">小号按钮</button>
  ```

+ 添加 **.btn-block** 类可以设置块级按钮，占据整行

+ **.active** 类可以设置按钮点击后样式， **disabled** 属性可以设置按钮是不可点击的。 注意 \<a> 元素不支持 disabled 属性，你可以通过添加 **.disabled** 类来禁止链接的点击。

  ```html
  <button type="button" class="btn btn-primary active">点击后的按钮</button>
  <button type="button" class="btn btn-primary" disabled>禁止点击的按钮</button>
  <a href="#" class="btn btn-primary disabled">禁止点击的链接</a>
  ```



#### 按钮组

---

+ 在 **\<div>** 元素上添加 **.btn-group** 类来创建按钮组，将内部按钮显示在同一行。

+ 使用 **.btn-group-lg|sm** 类来设置按钮组的大小

+ 使用 **.btn-group-vertical** 类来创建垂直的按钮组

+ 内嵌按钮组（水平或垂直）及下拉菜单

  ```html
  <div class="btn-group">
    <button type="button" class="btn btn-primary">Apple</button>
    <button type="button" class="btn btn-primary">Samsung</button>
    <div class="btn-group">
      <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
         Sony
      </button>
      <div class="dropdown-menu">
        <a class="dropdown-item" href="#">Tablet</a>
        <a class="dropdown-item" href="#">Smartphone</a>
      </div>
    </div>
  </div>
  ```

+ 拆分按钮组（按钮后点击下拉）

  ```html
  <div class="btn-group">
    <button type="button" class="btn btn-primary">Sony</button>
    <button type="button" class="btn btn-primary dropdown-toggle dropdown-toggle-split" data-toggle="dropdown">
      <span class="caret"></span>
    </button>
    <div class="dropdown-menu">
      <a class="dropdown-item" href="#">Tablet</a>
      <a class="dropdown-item" href="#">Smartphone</a>
    </div>
  </div>
  ```



#### 徽章 Badges

---

徽章用于突出显示新的或未读的项。如需使用徽章，只需要将 **.badge** 类加上带有指定意义的颜色类 (如 **.badge-secondary**) 添加到 **\<span>** 元素上即可。 徽章可以根据**父元素**的大小的变化而变化。

```html
<span class="badge badge-primary">主要</span>
<span class="badge badge-secondary">次要</span>
<span class="badge badge-success">成功</span>
<span class="badge badge-danger">危险</span>
<span class="badge badge-warning">警告</span>
<span class="badge badge-info">信息</span>
<span class="badge badge-light">浅色</span>
<span class="badge badge-dark">深色</span>

<span class="badge badge-pill badge-info">药丸形状信息</span>
```



#### 进度条

进度条可以显示用户任务的完成过程。创建一个基本的进度条的步骤如下：

- 添加一个带有 **.progress** 类的 \<div>。
- 接着，在上面的 \<div> 内，添加一个带有 class **.progress-bar** 的空的 \<div>。
- 添加一个带有百分比表示的宽度的 style 属性，例如 **style="width:70%"** 表示进度条在 **70%** 的位置。
- 进度条高度默认为 16px。可以使用 CSS 的 `height` 属性来修改。
- 可在进度条内添加文本，如进度的百分比（内部div文本）
- 内部`div`设置颜色属性：`bg-success`、`bg-info`、`bg-warning`、`bg-danger`
- 添加 `.progress-bar-striped` 类来设置**条纹**进度条
- 使用 `.progress-bar-animated` 类可以为进度条添加动画



#### 分页

基本的分页可以在 **\<ul>** 元素上添加 **.pagination** 类。然后在 **\<li>** 元素上添加 **.page-item** 类

```html
<ul class="pagination">
  <li class="page-item disabled"><a class="page-link" href="#">Previous</a></li>
  <li class="page-item"><a class="page-link" href="#">1</a></li>
  <li class="page-item active"><a class="page-link" href="#">2</a></li>
  <li class="page-item"><a class="page-link" href="#">3</a></li>
  <li class="page-item"><a class="page-link" href="#">Next</a></li>
</ul>
```

+ 当前页可以使用 **.active** 类来高亮显示

+ **.disabled** 类可以设置分页链接不可点击

+ 在 \<ul>上使用**.pagination-lg** 类设置大字体的分页条目，**.pagination-sm** 类设置小字体的分页条目

+ **.breadcrumb** 和 **.breadcrumb-item** 类用于设置面包屑导航

  ```html
  <ul class="breadcrumb">
    <li class="breadcrumb-item"><a href="#">Photos</a></li>
    <li class="breadcrumb-item"><a href="#">Summer 2017</a></li>
    <li class="breadcrumb-item"><a href="#">Italy</a></li>
    <li class="breadcrumb-item active">Rome</li>
  </ul>
  ```

  

#### 列表组

---

创建列表组，可以在 **\<ul>** 元素上添加 **.list-group** 类, 在 **\<li>** 元素上添加 **.list-group-item** 类。

```html
<ul class="list-group">
  <li class="list-group-item disabled">First item</li>
  <li class="list-group-item active">Second item</li>
  <li class="list-group-item">Third item</li>
</ul>
```

+ 添加 **.active** 类来设置激活状态的列表项

+ **.disabled** 类用于设置禁用的列表项

+ 创建一个**链接的列表项（菜单）**，可以将 **\<ul>** 替换为 **\<div>** ， **\<a>** 替换 **\<li>**。如果你想鼠标悬停显示灰色背景就添加**.list-group-item-action** 类

+ 颜色设置

  ```html
  <div class="list-group">
      <a href="#" class="list-group-item list-group-item-action">激活列表项</a>
      <a href="#" class="list-group-item list-group-item-success">成功列表项</a>
      <a href="#" class="list-group-item list-group-item-secondary">次要列表项</a>
      <a href="#" class="list-group-item list-group-item-info">信息列表项</a>
      <a href="#" class="list-group-item list-group-item-warning">警告列表项</a>
      <a href="#" class="list-group-item list-group-item-danger">危险列表项</a>
      <a href="#" class="list-group-item list-group-item-primary">主要列表项</a>
      <a href="#" class="list-group-item list-group-item-dark">深灰色列表项</a>
      <a href="#" class="list-group-item list-group-item-light">浅色列表项</a>
  </div>
  ```



#### 卡片

```html
<div class="card bg-info text-white">
  <div class="card-header">头部</div>
  <div class="card-body">内容</div> 
  <div class="card-footer">底部</div>
</div>
```

+ 卡片支持的背景颜色类及文字颜色类： **.bg-primary**, **.bg-success**, **.bg-info**, **.bg-warning**, **.bg-danger**, **.bg-secondary**, **.bg-dark** 和 **.bg-light**；**text-white**、**text-dark**等。
+ 在**card-body**元素内使用 **.card-title** 类来设置卡片的标题 。 **.card-text** 类用于设置卡片正文的内容。 **.card-link** 类用于给链接设置颜色。
+ 在card内可以添加`<img class="card-img-top/bottom" ...>`元素来给卡片添加图片，在**img**元素后添加`<div class="card-img-overlay"></div>`替换**card-body**时，图片将作为背景。



#### 下拉菜单

```html
//以列表格式显示链接的上下文菜单
<div class="dropdown">
  <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
    Dropdown button
  </button>
  <div class="dropdown-menu">
    <a class="dropdown-item" href="#">Link 1</a>
    <a class="dropdown-item" href="#">Link 2</a>
    <a class="dropdown-item" href="#">Link 3</a>
  </div>
</div>
```

+ **.dropdown** 类用来指定一个下拉菜单。
+ 可以使用一个按钮或链接来打开下拉菜单， 按钮或链接需要添加 **.dropdown-toggle** 和 **data-toggle="dropdown"** 属性。\<div> 元素上添加 `.dropdown-menu` 类来设置实际下拉菜单，然后在下拉菜单的选项中添加 `.dropdown-item` 类。
+ `<div class="dropdown-divider"></div>`用于在下拉菜单中创建一个水平的分割线。
+ `<div class="dropdown-header">Dropdown header 1</div>`用于在下拉菜单中添加标题（提示性）。
+ `<a class="dropdown-item active/disabled" href="#">Active/Disabled</a>`选项高亮显示或禁用。
+ `<div class="dropdown-menu dropdown-menu-right">`下拉菜单右对齐。
+ `<div class="dropup">`替换dropdown将菜单向上弹出。



#### 折叠

```html
//折叠可以很容易的实现内容的显示与隐藏
<button data-toggle="collapse" data-target="#demo">折叠</button>
 
<div id="demo" class="collapse">
	Lorem ipsum dolor text....
</div>
```

+ **.collapse** 类用于指定一个折叠元素 (实例中的 \<div>); 点击按钮后会在隐藏与显示之间切换。
+ 控制内容的隐藏与显示，需要在 \<a> 或 \<button> 元素上添加 **data-toggle="collapse"** 属性。 **data-target="#id"** 属性是对应折叠的内容 (如果\<a>元素，则可使用**href**)。
+ 默认情况下折叠的内容是隐藏的，你可以添加 **.show** 类让内容默认显示

+ 手风琴示例：

  使用 **data-parent** 属性来确保所有的折叠元素在指定的父元素下，这样就能实现在一个折叠选项显示时其他选项就隐藏

  ```html
  <div id="accordion">
      <div class="card">
        <div class="card-header">
          <a class="card-link" data-toggle="collapse" href="#collapse1">选项一
          </a>
        </div>
        <div id="collapse1" class="collapse show" data-parent="#accordion">
          <div class="card-body"> #1 内容
          </div>
        </div>
      </div>
      <div class="card">
        <div class="card-header">
          <a class="collapsed card-link" data-toggle="collapse" href="#collapse2">
          选项二
        	</a>
        </div>
        <div id="collapse2" class="collapse" data-parent="#accordion">
          <div class="card-body">#2 内容
          </div>
        </div>
      </div>
      <div class="card">
        <div class="card-header">
          <a class="collapsed card-link" data-toggle="collapse" href="#collapse3"> 			选项三
          </a>
        </div>
        <div id="collapse3" class="collapse" data-parent="#accordion">
          <div class="card-body"> #3 内容
          </div>
        </div>
      </div>
  </div>
  ```



#### 导航

+ 水平导航

  ```HTML
  <ul class="nav">
    <li class="nav-item">
      <a class="nav-link" href="#">Link1</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="#">Link2</a>
    </li>
  </ul>
  ```

+ 在\<ul>元素中添加 **.justify-content-center** 类设置导航居中显示， **.justify-content-end** 类设置导航右对齐；添加 **.flex-column** 类用于创建垂直导航。

+ 选项卡导航（可结合下拉菜单）

  ```html
  //使用 .nav-tabs 类可以将导航转化为选项卡。然后对于选中的选项使用 .active 类来标记。
  <ul class="nav nav-tabs">
    <li class="nav-item">
      <a class="nav-link active" href="#">Active</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="#">Link</a>
    </li>
    <li class="nav-item">
      <a class="nav-link disabled" href="#">Disabled</a>
    </li>
    <li class="nav-item dropdown">
      <a class="nav-link dropdown-toggle" data-toggle="dropdown" 				href="#">Dropdown</a>
      <div class="dropdown-menu">
        <a class="dropdown-item" href="#">Link 1</a>
        <a class="dropdown-item" href="#">Link 2</a>
        <a class="dropdown-item" href="#">Link 3</a>
      </div>
    </li>
  </ul>
  ```

+ 将`nav-tabs`改成`.nav-pills `类可以将导航项设置成胶囊形状。

+ 在\<ul>中添加**.nav-justified** 类可以设置导航项齐行等宽显示。

+ 动态选项卡

  ```html
  //选项卡是动态可切换的，可以在每个链接上添加 data-toggle="tab" 属性。 然后在每个选项对应的内容的上添加 .tab-pane 类。
  //淡入效果可以在 .tab-pane 后添加 .fade类:
  <!-- Nav tabs -->
  <ul class="nav nav-tabs">
    <li class="nav-item">
      <a class="nav-link active" data-toggle="tab" href="#home">Home</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" data-toggle="tab" href="#menu1">Menu 1</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" data-toggle="tab" href="#menu2">Menu 2</a>
    </li>
  </ul>
   
  <!-- Tab panes -->
  <div class="tab-content">
    <div class="tab-pane active container" id="home">...</div>
    <div class="tab-pane container" id="menu1">...</div>
    <div class="tab-pane container" id="menu2">...</div>
  </div>
  ```



#### 导航栏

导航栏一般放在页面的顶部。使用 **.navbar** 类来创建一个标准的导航栏，后面紧跟: **.navbar-expand-xl|lg|md|sm** 类来创建响应式的导航栏 (大屏幕水平铺开，小屏幕垂直堆叠)。

```html
<!-- 小屏幕上水平导航栏会切换为垂直的 -->
<nav class="navbar navbar-expand-sm bg-light">
 
  <!-- Links -->
  <ul class="navbar-nav">
    <li class="nav-item">
      <a class="nav-link" href="#">Link 1</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="#">Link 2</a>
    </li>
    <li class="nav-item">
      <a class="nav-link" href="#">Link 3</a>
    </li>
  </ul>
 
</nav>
```

+ 通过删除 **.navbar-expand-xl|lg|md|sm** 类来创建垂直导航栏。

+ 不同颜色导航栏：**.bg-primary**, **.bg-success**, **.bg-info**, **.bg-warning**, **.bg-danger**, **.bg-secondary**, **.bg-dark** 和 **.bg-light**。

+ 使用**navbar-light**、**navbar-dark**等设置文字颜色。

+ 在\<nav>元素中添加`<a class="navbar-brand" href="#"><img></a>`来设置图片自适应导航栏。

+ 自动折叠导航栏

  ```html
  <nav class="navbar navbar-expand-md bg-dark navbar-dark">
    <!-- Brand -->
    <a class="navbar-brand" href="#">Navbar</a>
   
    <!-- Toggler/collapsibe Button -->
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#collapsibleNavbar">
      <span class="navbar-toggler-icon"></span>
    </button>
   
    <!-- Navbar links -->
    <div class="collapse navbar-collapse" id="collapsibleNavbar">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" href="#">Link</a>
        </li>
          <!-- Dropdown -->
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">Dropdown link
            </a>
            <div class="dropdown-menu">
              <a class="dropdown-item" href="#">Link 1</a>
              <a class="dropdown-item" href="#">Link 2</a>
              <a class="dropdown-item" href="#">Link 3</a>
            </div>
      </li>
      </ul>
    </div> 
  </nav>
  ```

+ 导航栏中的输入框和按钮

  ```html
  <nav class="navbar navbar-expand-sm bg-dark navbar-dark">
    <form class="form-inline">
      <input class="form-control" type="text" placeholder="Search">
      <button class="btn btn-success" type="submit">Search</button>
    </form>
  </nav>
  ```

+ 导航栏中的文本，可以保证水平对齐，颜色与内边距一样

  ```html
  <span class="navbar-text">
      Navbar text
   </span>
  ```

+ 导航栏可以固定在头部或者底部，使用**.fixed-top** 或者**.fixed-bottom**



#### 面包屑导航 Breadcrumb

面包屑导航是一种基于网站层次信息的显示方式。它们表示当前页面在导航层次结构内的位置，是在用户界面中的一种导航辅助。

```html
<ol class="breadcrumb">
  <li class="breadcrumb-item"><a href="#">Home</a></li>
  <li class="breadcrumb-item"><a href="#">Library</a></li>
  <li class="breadcrumb-item active">Data</li>
</ol>
<!--或者如下形式-->
<nav class="breadcrumb">
  <a class="breadcrumb-item" href="#">Home</a>
  <a class="breadcrumb-item" href="#">Library</a>
  <a class="breadcrumb-item" href="#">Data</a>
  <span class="breadcrumb-item active">Bootstrap</span>
</nav>
```



#### 表单

表单元素 **\<input>**, **\<textarea>**, 和 **\<select>** elements 在使用 **.form-control** 类的情况下，宽度都是设置为 100%。

+ 堆叠表单

  ```html
  <form>
    <div class="form-group">
      <label for="email">Email address:</label>
      <input type="email" class="form-control" id="email">
    </div>
    <div class="form-group">
      <label for="pwd">Password:</label>
      <input type="password" class="form-control" id="pwd">
    </div>
    <div class="form-check">
      <label class="form-check-label">
        <input class="form-check-input" type="checkbox"> Remember me
      </label>
    </div>
    <button type="submit" class="btn btn-primary">Submit</button>
  </form>
  ```

+ 内联表单：所有内联表单中的元素都是左对齐的，内联表单需要在 **\<form>** 元素上添加 **.form-inline**类，**在屏幕宽度小于 576px 时为垂直堆叠，如果屏幕宽度大于等于576px时表单元素才会显示在同一个水平线上。**



#### 表单控件

支持 `input`、`textarea`、`checkbox`、`radio`、`select`。

```html
<div class="form-group">
  <label for="usr">用户名:</label>
  <input type="text" class="form-control" id="usr">
</div>
```

+ **Input**：支持所有的 HTML5 输入类型 type=* : text, password, datetime, datetime-local, date, month, time, week, number, email, url, search, tel, 以及 color。如果 input 的 type 属性未正确声明，输入框的样式将不会显示。

+ **textarea**：`<textarea class="form-control" rows="5" id="comment"></textarea>`

+ **checkbox**：

  ```html
  <div class="form-check"> <!--使用 .form-check-inline 类可以让选项显示在同一行上-->
    <label class="form-check-label">
      <input type="checkbox" class="form-check-input" value="">Option 1
    </label>
  </div>
  <div class="form-check">
    <label class="form-check-label">
      <input type="checkbox" class="form-check-input" value="">Option 2
    </label>
  </div>
  <div class="form-check disabled">
    <label class="form-check-label">
      <input type="checkbox" class="form-check-input" value="" disabled>Option 3
    </label>
  </div>
  ```

+ **radio**：

  ```html
  <div class="radio">
    <!--使用 .radio-inline 类可以让选项显示在同一行上-->
    <label class="radio-inline">
        <input type="radio" name="optradio">Option 1
    </label>
  </div>
  ```

+ **select**：

  ```html
  <div class="form-group">
    <label for="sel1">下拉菜单:</label>
    <select class="form-control" id="sel1">
      <option>1</option>
      <option>2</option>
      <option>3</option>
      <option>4</option>
    </select>
  </div>
  ```



#### 输入框组 Input group

```html
<form>
  <div class="input-group mb-3">
    <div class="input-group-prepend">
      <span class="input-group-text">@</span>
    </div>
    <input type="text" class="form-control" placeholder="Username">
  </div>
 
  <div class="input-group mb-3">
    <input type="text" class="form-control" placeholder="Your Email">
    <div class="input-group-append">
      <span class="input-group-text">@runoob.com</span>
    </div>
  </div>
</form>
```

+ 使用 **.input-group** 类来向表单输入框中添加更多的样式，如图标、文本或者按钮。

+ 使用 **.input-group-prepend** 类可以在输入框的的前面添加文本信息， **.input-group-append** 类添加在输入框的后面。

+ 使用 **.input-group-text** 类来设置文本的样式。

+ 使用 **.input-group-sm** 类来设置小的输入框， **.input-group-lg** 类设置大的输入框。

+ 多输入框和多文本

  ```html
  <!-- 多个输入框 -->
  <form>
    <div class="input-group mb-3">
      <div class="input-group-prepend">
        <span class="input-group-text">Person</span>
      </div>
      <input type="text" class="form-control" placeholder="First Name">
      <input type="text" class="form-control" placeholder="Last Name">
    </div>
  </form>
   
  <!-- 多个文本信息 -->
  <form>
    <div class="input-group mb-3">
      <div class="input-group-prepend">
        <span class="input-group-text">One</span>
        <span class="input-group-text">Two</span>
        <span class="input-group-text">Three</span>
      </div>
      <input type="text" class="form-control">
    </div>
  </form>
  ```

+ 其中输入框前后添加的组件可以是文本、复选框、单选框、按钮、按钮组、下拉菜单等。



#### 自定义表单

自定义一些表单的样式来替换浏览器默认的样式。

+ 自定义复选框

  ```html
  <form>
    <div class="custom-control custom-checkbox">
      <input type="checkbox" class="custom-control-input" id="customCheck">
      <label class="custom-control-label" for="customCheck">自定义复选框</label>
    </div>
  </form>
  ```

+ 自定义单选框

  ```html
  <form>
    <div class="custom-control custom-radio">
      <input type="radio" class="custom-control-input" id="customRadio" value="1">
      <label class="custom-control-label" for="customRadio">自定义单选框</label>
    </div> 
  </form>
  ```

+ 自定义控件显示在同一行，添加`custom-control-inline`

  ```html
  <form>
    <div class="custom-control custom-radio custom-control-inline">
      <input type="radio" class="custom-control-input" id="customRadio" name="example" value="customEx">
      <label class="custom-control-label" for="customRadio">自定义单选框 1</label>
    </div>
    <div class="custom-control custom-radio custom-control-inline">
      <input type="radio" class="custom-control-input" id="customRadio2" name="example" value="customEx">
      <label class="custom-control-label" for="customRadio2">自定义单选框 2</label>
    </div> 
  </form>
  ```

+ 自定义选择框Select，可以使用 **.custom-select-sm**、**.custom-select-lg** 来设置它们的大小

  ```html
  <form>
    <select name="cars" class="custom-select">
      <option selected>自定义选择菜单</option>
      <option value="Google">Google</option>
      <option value="Runoob">Runoob</option>
      <option value="Taobao">Taobao</option>
    </select>
  </form>
  ```

+ 自定义滑块控件

  ```html
  <form>
    <label for="customRange">自定义滑块控件</label>
    <input type="range" class="custom-range" id="customRange" name="points1">
  </form>
  ```

+ 自定义文件上传组件

  ```html
  <form>
    <div class="custom-file">
      <input type="file" class="custom-file-input" id="customFile">
      <label class="custom-file-label" for="customFile">选择文件</label>
    </div>
  </form>
  ```

  

#### 轮播图片

```html
<div id="demo" class="carousel slide" data-ride="carousel">
 
  <!-- 指示符 -->
  <ul class="carousel-indicators">
    <li data-target="#demo" data-slide-to="0" class="active"></li>
    <li data-target="#demo" data-slide-to="1"></li>
    <li data-target="#demo" data-slide-to="2"></li>
  </ul>
 
  <!-- 轮播图片 -->
  <div class="carousel-inner">
    <div class="carousel-item active">
      <img src="https://static.runoob.com/images/mix/img_fjords_wide.jpg">
      <!--描述文字-->
      <div class="carousel-caption">
        <h3>第一张图片描述标题</h3>
        <p>描述文字!</p>
  	  </div>
    </div>
    <div class="carousel-item">
      <img src="https://static.runoob.com/images/mix/img_nature_wide.jpg">
    </div>
    <div class="carousel-item">
      <img src="https://static.runoob.com/images/mix/img_mountains_wide.jpg">
    </div>
  </div>
 
  <!-- 左右切换按钮 -->
  <a class="carousel-control-prev" href="#demo" data-slide="prev">
    <span class="carousel-control-prev-icon"></span>
  </a>
  <a class="carousel-control-next" href="#demo" data-slide="next">
    <span class="carousel-control-next-icon"></span>
  </a>
 
</div>
```



#### 模态框 Modal

```html
<!-- 按钮：用于打开模态框 -->
<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
  打开模态框
</button>
 
<!-- 模态框 -->
<div class="modal fade" id="myModal">
  <div class="modal-dialog">
    <div class="modal-content">
 
      <!-- 模态框头部 -->
      <div class="modal-header">
        <h4 class="modal-title">模态框头部</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
 
      <!-- 模态框主体 -->
      <div class="modal-body">
        模态框内容..
      </div>
 
      <!-- 模态框底部 -->
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">关闭</button>
      </div>
 
    </div>
  </div>
</div>
```

+ 在`modal-dialog`后添加 **.modal-sm** 类来创建一个小模态框，**.modal-lg** 类可以创建一个大模态框。



#### 提示框 Title

+ 向元素添加 **data-toggle="tooltip"** 来来创建提示框。**title** 属性的内容为提示框显示的内容。
+ 在js初始化代码中：` $('[data-toggle="tooltip"]').tooltip(); `
+ 可以使用 **data-placement** 属性来设定提示框显示的方向: top, bottom, left 或 right。



#### 弹出框 Popover

弹出框控件类似于提示框，它在**鼠标点击**到元素后显示，与提示框不同的是它可以显示更多的内容。

+ 向元素添加 **data-toggle="popover"** 来来创建弹出框。**title** 属性的内容为弹出框的**标题**，**data-content** 属性显示了弹出框的**文本内容**。
+ 在js初始化代码中：`$('[data-toggle="popover"]').popover(); `
+ 使用 **data-placement** 属性来设定弹出框显示的方向: top, bottom, left 或 right。
+ 默认情况下，弹出框在再次点击指定元素后就会关闭，你可以使用 **data-trigger="focus"** 属性来设置在鼠标点击元素外部区域来关闭弹出框。
+ 实现在鼠标移动到元素上显示，移除后消失的效果，可以使用 **data-trigger** 属性，并设置值为 "**hover**"。



#### 滚动监听 Scrollspy

滚动监听（Scrollspy）插件，即自动更新导航插件，会根据滚动条的位置自动更新对应的导航目标。

```html
<!-- 可滚动区域 -->
<body data-spy="scroll" data-target=".navbar" data-offset="50">
 
    <!-- The navbar - The <a> elements are used to jump to a section in the scrollable area -->
    <nav class="navbar navbar-expand-sm bg-dark navbar-dark fixed-top">
    ...
      <ul class="navbar-nav">
        <li><a href="#section1">Section 1</a></li>
        ...
    </nav>

    <!-- 第一部分内容 -->
    <div id="section1">
      <h1>Section 1</h1>
      <p>Try to scroll this page and look at the navigation bar while scrolling!</p>
    </div>
    ...
 
</body>
```

要监听的元素（通常是 body）添加 **data-spy="scroll"** 。后添加 **data-target** 属性，它的值为导航栏的 id 或 class (**.navbar**)。这样就可以联系上可滚动区域。

注意可滚动项元素上的 id （**\<div id="section1">**） 必须匹配导航栏上的链接选项 （**\<a href="#section1">**)。

可选项**data-offset** 属性用于计算滚动位置时，距离顶部的偏移像素。 默认为 10 px。

**设置相对定位:** 使用 data-spy="scroll" 的元素需要将其 CSS **position** 属性设置为 "relative" 才能起作用。



#### 小工具

+ 使用 **border** 类可以添加或移除边框

  ```html
  <span class="border"></span>
  <span class="border border-0"></span>
  <span class="border border-top-0"></span>
  <span class="border border-right-0"></span>
  <span class="border border-bottom-0"></span>
  <span class="border border-left-0"></span>
  ```

+ 边框颜色

  ```html
  <span class="border border-primary"></span>
  <span class="border border-secondary"></span>
  <span class="border border-success"></span>
  <span class="border border-danger"></span>
  <span class="border border-warning"></span>
  <span class="border border-info"></span>
  <span class="border border-light"></span>
  <span class="border border-dark"></span>
  <span class="border border-white"></span>
  ```

+ 边框圆角设置

  ```html
  <span class="rounded"></span>
  <span class="rounded-top"></span>
  <span class="rounded-right"></span>
  <span class="rounded-bottom"></span>
  <span class="rounded-left"></span>
  <span class="rounded-circle"></span>
  <span class="rounded-0"></span>
  ```

+ 浮动：**.float-right** 类用于设置元素右浮动， **.float-left** 设置元素左浮动, **.clearfix** 类用于清除浮动

+ 响应式浮动

  ```html
  <div class="float-sm-right">在大于小屏幕尺寸上右浮动</div><br>
  <div class="float-md-right">在大于中等屏幕尺寸上右浮动</div><br>
  <div class="float-lg-right">在大于大屏幕尺寸上右浮动</div><br>
  <div class="float-xl-right">在大于超大屏幕尺寸上右浮动</div><br>
  <div class="float-none">没有浮动</div>
  ```

+ 使用 **.mx-auto** 类来设置居中对齐

+ 宽度设置

  ```html
  <div class="w-25 bg-warning">宽度 25%</div>
  <div class="w-50 bg-warning">宽度 50%</div>
  <div class="w-75 bg-warning">宽度 75%</div>
  <div class="w-100 bg-warning">宽度 100%</div>
  <div class="mw-100 bg-warning">最大宽度 100%</div>
  ```

+ 高度

  ```html
  <div style="height:200px;background-color:#ddd">
      <div class="h-25 bg-warning">高度 25%</div>
      <div class="h-50 bg-warning">高度 50%</div>
      <div class="h-75 bg-warning">高度 75%</div>
      <div class="h-100 bg-warning">高度 100%</div>
      <div class="mh-100 bg-warning" style="height:500px">最大高度 100%</div>
  </div>
  ```



#### 弹性布局 Flex

Bootstrap 3 与 Bootstrap 4 最大的区别就是 Bootstrap 4 使用弹性盒子来布局，而不是使用浮动来布局。

```html
<div class="d-flex p-3 bg-secondary text-white">
  <div class="p-2 bg-info">Flex item 1</div>
  <div class="p-2 bg-warning">Flex item 2</div>
  <div class="p-2 bg-primary">Flex item 3</div>
</div>
```

+ 使用 d-inline-flex 类将使弹性容器盒子根据内部元素占据一行的部分区域（默认占据整行）
+ **.flex-row** 可以设置弹性子元素水平靠左显示，这是默认的。使用 **.flex-row-reverse** 类用于设置右对齐显示，即与 **.flex-row** 方向相反。
+ **.flex-column** 类用于设置弹性子元素**垂直方向**显示, **.flex-column-reverse** 用于翻转子元素

+ **.justify-content-\*** 类用于修改弹性子元素的排列方式，***** 号允许的值有：**start (默认), end, center, between 或 around**
+ **.flex-fill** 类强制设置各个弹性子元素的宽度是一样的（默认平分整行）
+ **.flex-grow-1** 用于设置子元素使用剩下的空间。（占据此行中其他元素剩下的空间）
+ **.order-***类可以设置弹性子元素的排序，从 **.order-1** 到 **.order-12**，数字越低权重越高
+ **.mr-auto** 类可以设置子元素右外边距为 **auto**，即 **margin-right: auto!important;**，**.ml-auto** 类可以设置子元素左外边距为 **auto**，即 **margin-left: auto!important;**
+ 弹性容器中包裹子元素可以使用以下三个类： **.flex-nowrap (默认)**, **.flex-wrap** 或 **.flex-wrap-reverse**。设置 flex 容器是单行或者多行。
+ 使用 **.align-content-*** 来控制在**垂直方向**上如何去堆叠子元素，包含的值有：.align-content-start (默认), .align-content-end, .align-content-center, .align-content-between, .align-content-around 和 .align-content-stretch。这些类在只有一行的弹性子元素中是无效的。
+ 设置单行的子元素对齐可以使用 **.align-items-\*** 类来控制，包含的值有：**.align-items-start, .align-items-end, .align-items-center, .align-items-baseline, 和 .align-items-stretch (默认)**。
+ 设置**指定子元素**对齐对齐可以使用 .align-self-* 类来控制，包含的值有：.align-self-start, .align-self-end, .align-self-center, .align-self-baseline, 和 .align-self-stretch (默认)。
+ 以上类都可以结合不同屏幕大小进行调整



#### 多媒体对象

处理多媒体对象（图片或视频）和内容的布局。

```html
<div class="media border p-3">
  <img src="mobile-icon.png" alt="John Doe" class="mr-3 mt-3 rounded-circle" style="width:60px;">
  <div class="media-body">
    <h4>菜鸟教程</h4>
    <p>学的不仅是技术，更是梦想！！！</p>
  </div>
</div>
```

+ 多媒体对象可以多个嵌套（一个多媒体对象中包含另外一个多媒体对象）
+ 将头像图片显示在右侧，可以在 **.media-body** 容器后添加图片
+ 可以使用 **align-self-*** 相关类来设置多媒体对象的图片显示（垂直）位置