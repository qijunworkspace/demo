# Git 学习总结

> 软件配置管理SCM: 一种标识、组织和控制更改的技术, 贯穿整个软件生命周期中建立和维护项目产品的完整性。
    通过执行版本控制、变更控制的规程, 以及使用合适的配置管理软件, 来保证所有配置项的完整性和可跟踪性。
    常用的工具有 Microsoft VSS，CVS，SVN等。  
    
> 分布式版本控制: 每台电脑都是完整的版本库，通过中央版本库进行合并。  
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
          |                          |                        |       git pull          |
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
+ `git merge otherBranch` 将otherBranch合并到当前分支
+ `git merge origin/master <branch> --no-off` 将两个分支合并，不使用Fast-Forword模式， 会创建commit
