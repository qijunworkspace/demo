 # Maven

 ## 1、Maven命令

**执行maven命令必须进入到pom.xml的目录中进行执行**

 > `mvn help:effective-pom`  
 > `mvn clean install -Dmaven.test.skip=true`  
 > `mvn dependency:tree`   
 > `mvn dependency:resolve`  
 > `mvn dependency:analyze`  
 > `mvn clean install -Pproduction -X`  
 > `mvn site:run`  





## 2、基础概念

**Maven是一款服务于java平台的自动化构建工具** (约定重于配置)：

+ **构建的定义**：把动态的Web工程经过**编译**得到的编译结果**部署**到服务器上的整个过程。

+ **构建的环节**：

  + **清理 clean**：将以前编译得到的旧文件class字节码文件删除
  + **编译 compile**：将java源程序编译成class字节码文件
  + **测试 test**：自动测试，自动调用junit程序
  + **报告 report**：测试程序执行的结果
  + **打包 package**：动态Web工程打War包，java工程打jar包
  + **安装 install**：Maven特定的概念-----将打包得到的文件复制到“仓库”中的指定位置
  + **部署 deploy**：将动态Web工程生成的war包复制到Servlet容器下，使其可以运行

+ **工程目录**：

  > 根目录：工程名
  > |---src：源码
  > |---|---main: 存放主程序
  > |---|---|---java：java源码文件
  > |---|---|---resource：存放框架的配置文件
  > |---|---test：存放测试程序
  > |---pom.xml：maven的核心配置文件

+ **依赖范围 Scope**：

  >**compile，**默认值，适用于所有阶段（开发、测试、部署、运行），本jar会一直存在所有阶段。
  >
  >**provided，**只在开发、测试阶段使用，目的是不让Servlet容器和你本地仓库的jar包冲突 。如servlet.jar。
  >
  >**runtime，**只在运行时使用，如JDBC驱动，适用运行和测试阶段。
  >
  >**test，**只在测试时使用，用于编译和运行测试代码。不会随项目发布。
  >
  >**system，**类似provided，需要显式提供包含依赖的jar，Maven不会在Repository中查找它。

+ **build配置**：

  ```xml
  <build>
  　　<!-- 项目的名字 -->
  　　<finalName>WebMavenDemo</finalName>
  　　<!-- 描述项目中资源的位置 -->
  　　<resources>
  　　　　<!-- 自定义资源1 -->
  　　　　<resource>
  　　　　　　<!-- 资源目录 -->
  　　　　　　<directory>src/main/java</directory>
  　　　　　　<!-- 包括哪些文件参与打包 -->
  　　　　　　<includes>
  　　　　　　　　<include>**/*.xml</include>
  　　　　　　</includes>
  　　　　　　<!-- 排除哪些文件不参与打包 -->
  　　　　　　<excludes>
  　　　　　　　　<exclude>**/*.txt</exclude>
  　　　　　　　　　　<exclude>**/*.doc</exclude>
  　　　　　　</excludes>
  　　　　</resource>
  　　</resources>
  　　<!-- 设置构建时候的插件 -->
  　　<plugins>
  　　　　<plugin>
  　　　　　　<groupId>org.apache.maven.plugins</groupId>
  　　　　　　<artifactId>maven-compiler-plugin</artifactId>
  　　　　　　<version>2.1</version>
  　　　　　　<configuration>
  　　　　　　　　<!-- 源代码编译版本 -->
  　　　　　　　　<source>1.8</source>
  　　　　　　　　<!-- 目标平台编译版本 -->
  　　　　　　　　<target>1.8</target>
  　　　　　　</configuration>
  　　　　</plugin>
  　　　　<!-- 资源插件（资源的插件） -->  
  　　　　<plugin>  
  　　　　　　<groupId>org.apache.maven.plugins</groupId>  
  　　　　　　<artifactId>maven-resources-plugin</artifactId>  
  　　　　　　<version>2.1</version>  
  　　　　　　<executions>  
  　　　　　　　　<execution>  
  　　　　　　　　　　<phase>compile</phase>  
  　　　　　　　　</execution>  
  　　　　　　</executions>  
  　　　　　　<configuration>  
  　　　　　　　　<encoding>UTF-8</encoding>  
  　　　　　　</configuration> 
  　　　　</plugin>
  　　　　<!-- war插件(将项目打成war包) -->  
  　　　　<plugin>  
  　　　　　　<groupId>org.apache.maven.plugins</groupId>  
  　　　　　　<artifactId>maven-war-plugin</artifactId>  
  　　　　　　<version>2.1</version>  
  　　　　　　<configuration>
  　　　　　　　　<!-- war包名字 -->  
  　　　　　　　　<warName>WebMavenDemo1</warName>
  　　　　　　</configuration>  
  　　　　</plugin>  
  　　</plugins>
  </build>
  ```

  