# Git 学习总结

> 软件配置管理SCM: 一种标识、组织和控制更改的技术, 贯穿整个软件生命周期中建立和维护项目产品的完整性。 通过执行版本控制、变更控制的规程, 以及使用合适的配置管理软件, 来保证所有配置项的完整性和可跟踪性。 常用的工具有 Microsoft VSS，CVS，SVN等。  

> 分布式版本控制: 每台电脑都是完整的版本库，通过中央版本库进行合并。  
>
> Git: 分布式、速度快、branch灵活、跟踪文件的修改而非文件


### 1. 使用方式图
    +++++++++++++               +++++++++++              +++++++++++      #        +++++++++++
    + Working   +               + Staging +              +  Local  +      #        +  Remote +
    + Directory +               + Area    +              +  Repo   +      #        +  Repo   +
    +++++++++++++               +++++++++++              +++++++++++      #        +++++++++++
          |                          |                        |           #             |
          |       git add            |                        |           #             |
          |  ------------------->    |                        |           #             |
          |                          |       git commit       |           #             |
          |                          |  ------------------->  |           #             |
          |                          |                        |       git push          |
          |                          |                        | --------------------->  |
          |                          |                        |           #             |
          |                          |                        |           #             |
          |                          |                        |           #             |
          |                          |                        |    git pull/fetch       |
          |                          |                        | <---------------------  |
          |                          |                        |           #             |
          |                     git checkout                  |           #             |
          | <-----------------------------------------------  |           #             |
          |                          |                        |           #             |
          |                     git merge                     |           #             |
          | <-----------------------------------------------  |           #             |
          |                          |                        |           #             |
          |                          |                        |           #             |
    +++++++++++++               +++++++++++              +++++++++++      #        +++++++++++
    + Working   +               + Staging +              +  Local  +      #        +  Remote +
    + Directory +               + Area    +              +  Repo   +      #        +  Repo   +
    +++++++++++++               +++++++++++              +++++++++++      #        +++++++++++

+ 工作区： 本地实际的文件位置
+ 暂存区： 下一次需要提交的文件修改
+ 本地仓库： 本地的代码分支管理仓库(HEAD指向当前分支)
  
### 2. 常用操作命令
+ `git init` 在当前文件夹创建仓库
+ `git reflog` 查看仓库版本历史(commit/pull/merge/checkout等)
+ `git checkout -- file` 丢弃file在工作区的修改，恢复为最近一次 commit/add 时的状态，必须加上--
+ `git diff file` 查看当前文件和暂存区文件的区别
+ `git diff --staged` 比较暂存区和版本库差异
+ `git diff <branch1> <branch2>` 比较两个分支之间的差异
+ `git diff --cached` 比较暂存区和版本库的差异
+ `git diff <id1> <id2>` 比较两次提交之间的差异
+ `git reset HEAD <file>` 将暂存区的文件修改撤销，回退到工作区中
+ `git reset --soft/--hard` 回退到最后一次提交的版本状态
+ `git add file` 将文件的修改添加到暂存区
+ `git commit -m 'comment'` 将暂存区的修改一起提交本地仓库
+ `git rm file` 从版本库删除文件，还需要commit一次
+ `git log --graph --oneline` 查看版本提交历史
+ `git log <file>` 查看文件每次提交记录
+ `git log -p <file>` 查看每次详细修改内容的diff

### 3. 分支Branch/标签Tag 命令
+ `git checkout <branch/tag>` 切换到对应版本/标签
+ `git branch` 查看本地所有分支(其中*表示当前分支)
+ `git branch -r` 查看远程分支
+ `git checkout -b <newBranch>` 新建分支并切换到该分支
+ `git branch -m name newName` 修改分支名称
+ `git branch -d name` 删除分支，-D表示强制删除
+ `git branch --merged/--no-merged` 查看已经/尚未合并到当前分支的所有分支名称
+ `git merge otherBranch [--no-ff]` 将otherBranch合并到当前分支(--no-ff表示不使用Fast-Forward快进合并模式, 会生成merge提交)
+ `git merge origin/master` 合并远程分支到当前分支
+ `git rebase master <branch>` 合并branch分支到master分支
+ `git tag` 查看当前版本库中所有标签(标签用于固化提交版本，带有唯一名称)
+ `git tag tagName [commit_id]` 新建标签，commit_id表示指定提交版本号
+ `git tag -a v1.0 [-m 'comment']` 标记当前分支为1.0版本并添加注释
+ `git tag -l 'v1.*'` 查看1.0版本下有多少小版本
+ `git tag -d v0.1` 删除本地标签信息
+ `git stash` 将当前工作现场保存(当前修改没有完成时，在切换其他版本前将现场保存)
+ `git stash list` 查看所有暂存的工作现场
+ `git stash apply id -> git stash drop id` 取回工作现场并删除
+ `git stash pop` 取回工作现场并删除
+ `git stash clear` 清空暂存队列

### 4. 远程仓库操作命令
+ `git clone path` 克隆仓库，可以使用https协议或git协议(ssh)，默认克隆master分支
+ `git remote -v` 查看远程服务器地址和仓库名称
+ `git remote show origin` 查看远程服务器的仓库状态
+ `git remote add origin git@github.com:qijunworkspace/demo.git` 添加远程仓库地址
+ `git remote rm name` 删除本地记录的远程仓库
+ `git pull [--no-ff] [origin <remoteBranch>:<localBranch>]` 获取远程仓库所有分支更新并合并到本地当前分支，等价于fetch+merge
+ `git fetch origin master` 获取远程仓库origin的master分支更新
+ `git checkout --track origin/branch` 跟踪某个远程分支并创建同名的本地分支
+ `git checkout -b <localBranch> origin/<remoteBranch>` 跟踪某个远程分支并创建指定的本地分支
+ `git push origin master` 将本地主分支推送到远程主分支
+ `git push -u origin branch` 将本地master或dev分支推送到远程仓库(建立track关系)，远程分支没有分支时将创建，-u用于指定默认upstream
+ `git push --set-upstream origin develop` 指定develop分支的upstream, 同上
+ `git branch --set-upstream-to=origin/develop develop ` 设置本地库和远程库的跟踪关系
+ `git push origin <localBranch>:<remoteBranch>` 创建远程分支
+ `git push origin :<remote_branch>` 删除本地分支后，执行此命令再删除远程分支

### 5. 配置Git环境
+ `git config --global color.ui true` 设置显示颜色
+ `git config --global user.name qijun` 设置用户信息
+ `git config --global user.email qijunworkspace@163.com` 设置用户信息
+ `git config --global alias.lg "log --color --graph --pretty=format:'%Cred%h%Creset -%C(yellow)%d%Creset %s %Cgreen(%cr) %C(bold blue)<%an>%Creset' --abbrev-commit"` 设置日志样式
+ `git check-ignore -v file`  查看文件为什么被忽略

### 6. 实践总结
+ 文件修改必须add到暂存区后再统一执行commit, git记录的是修改信息
+ commit自动串成一条时间线，HEAD表示当前版本，HEAD^^及HEAD~2表示往上回溯两个版本
+ 版本回退时，仅改变HEAD的指向，然后更新工作区的文件
+ 创建版本库时，Git自动创建一个*master分支*，一般使用*origin*作为远程库的名称
+ master分支对应稳定版本，一般在dev分支上进行开发，发布版本时合并到master分支，dev分支表示最新开发状态
+ 大型团队开发使用Git Flow工具，添加Feature分支、Release分支及Hotfix分支
+ 每个开发人员建立自己的分支(本地)，定期向dev分支合并
> `git checkout -b dev origin/dev` 建立分支并与远程分支对应  
>
> `git push origin dev` 向远程推送dev分支  
>
> `git push --set-upstream dev origin/dev` 将本地dev分支与远程origin/dev分支关联  
>
> `git pull origin/dev dev` 拉取远程分支的内容  
>
> `git push origin/dev dev` 推送dev分支到远程  
>
> `git branch -r` 查看远程分支  
>
> `git checkout -b dev origin/dev` 将远程的分支迁移到本地并切换到该分支  
>
> `git update-index --assume-unchanged src/main/resources/config/db.properties`  取消本地配置文件的上传

