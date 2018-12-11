# 命令行使用记录
> 记录工作中使用过的命令。

## 1. Linux

#### 1.1 文件
1. `pwd` 查看当前命令行路径
2. `locate filename` 通过名称查找文件
3. `find sth` 在当前目录下查找目录或文件
4. `touch file` 更改文件的最后修改时间
5. `stat file` 显示文件详细信息
6. `ls [path]` 显示所有文件名， -ah可显示隐藏文件
7. `ll` 显示所有文件详情(权限，用户，日期，大小)，等于`ls -l`， -ah可显示隐藏文件
*****

> 目录操作添加 -r 表示递归
1. `mkdir dir` 创建目录
2. `cp item1 [item2...] dest` 复制文件或目录到 dest文件或目录
3.  `scp [-r] item dest` 远程拷贝文件或目录
4. `mv item1 [item2...] dest` 移动文件或目录到 dest文件或目录
5. `rm item1 [...]` 删除文件或目录，使用通配符前应使用ls检查下，防止误删
6. `ln item link` 创建硬链接(等价于文件本身-INode，不能关联目录，只能关联同一磁盘上的文件)
7. `ln -s item link` 创建符号链接(类似快捷方式)
*****


#### 1.2 网络
1. `ifconfig` 显示所有网络接口的属性
2. `iptables -L` 查看防火墙的设置
3. `netstat -s` 查看网络统计信息
4. `netstat -nelp| grep 8080` 查看指定端口的信息


#### 1.3 系统状态
1. `uname -a` 查看内核版本，操作系统信息
2. `env` 查看系统环境变量
3. `free -m` 查看内存/交换区使用情况
4. `df -h` 查看磁盘使用情况
5. `ps -ef` 查看所有进程信息(用户，pid, 启动时间等)， 添加`aux`显示CPU，内存等
6. `top` 实时显示进程状态(pid,CPU使用率(空闲、内核/用户进程、IO等待)，内存使用率，平均负载(等待运行的进程比例)等)

#### 1.4 软件包
> 两大包管理技术: ".deb" 和 ".rpm"  
> 软件包管理系统由底层工具(dpkg/rpm)和上层工具(apt-get/yum)组成

1. `apt-cache search sth`, `yum search sth` 软件包查找
1. `apt-get install sth`, `yum install sth` 软件包安装
3. `dpkg --install package_file`, `rpm -i package_file` 底层软件包文件安装(没有依赖解析)
4. `apt-get remove sth`, `yum erase sth` 软件包删除
5. `apt-get update/upgrade`, `yum update` 软件包更新
6. `dpkg --list`, `rpm -qa`, `yum list installed` 查看已经安装的软件
7. `dpkg --status sth`, `rpm -q sth` 查看软件包的状态
7. `rpm -e --nodeps mysql-xxx` 删除对应的软件

#### 1.5 使用及服务
1. `whereis java` 查找服务执行文件路径，或 `which java`
2. `chkconfig --list` 显示系统所有服务
3. `systemctl start/restart/stop/status serviceName` 启动/重启/停止/显示服务状态


#### 1.6 用户权限
1. `id` 查看当前用户身份信息
2. `ls -l file` 查看文件权限
3. 输出行的前十个字符
    > 首字符: -(普通文件)、d(目录)、l(符号链接)、c(字符设备文件)、b(块设备文件)  
    > 后九位0-7: |---文件所有者权限|---用户组权限|---其他人权限| r(写权限)、w(读权限)、x(执行权限) 
4. `chmod 777 file` 修改文件的访问权限
5. `chown owner:group file` 更改文件的所有权

## 2. Windows



## 3. SQL
数据库相关操作。

#### 3.1 Mysql
+ 用户操作
> `mysql -uroot -p` 登录mysql命令行  
> `user mysql` 使用mysql数据库实例  
> `create user 'xxx'@'%' identified by 'password'` 创建用户  
> `update user set host='%' where user='root' and host='localhost'` 更新用户访问地址  
> `grant all privileges on dbname.* to 'xxx'@'%‘` 授予用户所有权限(SELECT,INSERT,UPDATE,DELETE,CREATE,DROP,ALTER)  
> `flush privileges` 刷新权限配置，使之生效

+ 实例操作
> `show databases` 显示mysql所有数据库实例  
> `use database_1` 切换到实例  
> `show tables` 显示系统所有表格

+ 备份还原
> `mysqldump -u 用户名 -p 数据库名> backup.sql ` 备份数据库  
> `mysql --socket=/var/lib/mysql/mysql.sock -u 用户名 -p 数据库名< Solin.sql`  导入数据库文件  
> 可选参数 `--socket=/var/lib/mysql/mysql.sock --add-drop-table --default-character-set=utf8 --single-transaction=TRUE` 
 
 
 #### 3.2 Oracle