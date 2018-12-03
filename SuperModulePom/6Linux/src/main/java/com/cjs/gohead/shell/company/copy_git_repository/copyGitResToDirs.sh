#!/usr/bin/env bash

## 这个脚本在容器里面运行.

destDir=${1}
jobName=${2}
user=${3}
group=${4}
gitRepository=${5}
dockerName=${6}

#1. 进入到job的目录, 即workspace.
cd ${destDir}

# 2. 复制git仓库的到job.

echo "Now in directory(${destDir})"
jobAbsolutePath=${destDir}/${jobName}
${destDir}/createDirAndchGRPAndOwn.sh ${jobName} ${user} ${group}

if [ $? == 0 ]; then
    cd ${jobAbsolutePath}


    if [ -d .git ]; then
        echo "${jobAbsolutePath} directory/job in ${dockerName} docker is already a git repository."

        # 移除目录下的所有文件
        if [ "$(ls ./)" ]; then
            rm -r * && echo "Visible files and dirs are removed." || exit 1
        else
            echo "There are no other visible files in old git repository."
        fi

        if [ "$(ls -A ./)" ]; then
            rm -rf .[^.] .??* && echo "Hidden files and dirs are removed." || exit 1
        else
            echo "There are no other hidden files in old git repository."
        fi

        echo "Old git repository is removed."
    else
        echo "${jobAbsolutePath} directory in ${dockerName} is not a git repository."
    fi

    # 只复制.git目录(减少多余文件的复制, 比如编辑器产生的target/和bin/目录);
    # 复制后执行git reset --hard或者git checkout .(保证index和HEAD处于相同状态)操作即可.
    cp -rp ${gitRepository} ${jobAbsolutePath} || exit 1
    echo "The process of copying git repository(from ${gitRepository} to ${jobAbsolutePath}) finish."

    # 执行git rest --hard操作
    git reset --hard 1>/dev/null || exit 1
    echo "Git rest --hard finish."

    chgrp -R ${group} ${jobAbsolutePath} && chown -R ${user} ${jobAbsolutePath} || exit 1

    # 回到上一级目录(destDir), 以备下一次循环创建目录.
    cd ${destDir}

else
    exit 1
fi
