#!/bin/bash

SCRIPT_DIR=$(dirname $(readlink -e $0))
### 使用方法:
###   1. 首先将git库(只需.git目录即可)和此目录的三个脚本复制到想要此git库的docker所在的宿主机.
###   e.g. scp -r userName@hostWithGit:GitDir/.git destDir(等于脚本中的gitRepository变量);
###        scp -r userName@hostWithGit:shellDir destDir.
###   2. 在docker所在宿主机上执行copyGitResToDockers.sh这个脚本即可; job名字在下面的变量jobNameArr中配置,
### docker名字采用预定义变量的形式读入. 其余一些配置参数见注释.
###   usage: ./copyGitResToDockers.sh dockerName1 dockerName2 ...
###   如果想要git库的docker的宿主机有多个, 目前只能在每一个宿主机上都进行上面的步骤.

## 配置参数
# job所在目录.
destDir=/home/jenkins/workspace
# 宿主机的git仓库.
gitRepository=~/New_Company_WorkSpace//.git
# 要复制宿主机git库的job名字. NOTE: 支持多层目录的格式(e.g. k2platform.k2db.realtime-access.build/k2db).
jobNameArr=("k2platform.k2db.realtime-access.build" "k2platform.k2db.realtime-ingestion.build" \
            "k2platform.k2db.k2dbid.build" "k2platform.k2db.k2dbim.build" "k2platform.k2db.rest.build" \
            "k2platform.k2db.stats.build" "k2platform.k2db.haproxy.build" "k2platform.k2db.k2dbidsb.build" \
            "k2platform.k2db.k2dbidss.build")
# 创建的job所属用户
user=jenkins
# 创建的job所属group
group=jenkins
## 配置参数

# git仓库要复制的docker名字, 由用户输入.
dockerNameArray=( "$@" )

## 复制git仓库到每一个docker的每一个job里.
for i in "${dockerNameArray[@]}"
do
  dockerName=${i}

  echo "The process of copying git repository to docker(${dockerName}) start------------------------------------------" && echo

  #1. 复制所需的脚本和宿主机的git库到容器内.
  docker cp ${SCRIPT_DIR}/copyGitResToDirs.sh  ${dockerName}:${destDir} && \
  docker cp ${SCRIPT_DIR}/createDirAndchGRPAndOwn.sh  ${dockerName}:${destDir} && \
  docker cp ${gitRepository}  ${dockerName}:${destDir}

  #2. 在容器内执行copyGitResToDirs.sh脚本
  if [ $? == 0 ]; then
    for j in "${jobNameArr[@]}"
    do
        jobName=${j}
        echo "The process of copying git repository to job(${jobName}) start----------------"

        docker exec -i ${dockerName} bash -c "${destDir}/copyGitResToDirs.sh ${destDir} ${jobName} ${user} ${group} ${destDir}/.git ${dockerName}"

        echo "The process of copying git repository to job(${jobName}) end----------------" && echo
    done
  else
    echo "Docker cp fail."
  fi

  echo "The process of copying git repository to docker(${dockerName}) end------------------------------------------" && echo
done