#!/bin/bash

## 根据文件模式在目录递归地找出与文件模式相匹配的文件(只有文件将会被匹配, 不包括目录), 然后对这些文件进行chmod操作.

# 所要递归搜索的目录
directory=${1}
echo "The directory that find and chmod execute: ${directory}"
# 文件模式
filePattern=${2}
echo "The pattern of file name: ${filePattern}"
# 对匹配到文件所进行的操作
operation=${3}
echo "The operation: ${operation}"

### Harvest
# Without the quotes(single or double), the shell will interpret *.sh as a glob pattern and expands it to any
# file names matching the glob before passing it to find command.
# This way, if you had, say, foo.java in the current directory, find's actual command line would be:
# find . -name foo.java
###
directories=`find ${directory} -type f -name "${filePattern}"`

for eleFile in ${directories}; do
    chmodCommand="chmod ${operation} ${eleFile}"
    `${chmodCommand}`
    sub=`expr substr ${operation} 1 1`
    oper=`expr substr ${operation} 2 1`
    obj=`expr substr ${operation} 3 1`
    echo "Execute ${sub} ${oper} ${obj} on ${eleFile}"
done
