#!/bin/bash

# 方式1
check_ls(){
    if [ -f "/bin/ls" ]
    then
         return 0
    else
         return 1
    fi
}

if  check_ls; then
    echo 文件存在
else
    echo 文件不存在
fi

# 方式2
# The return value from a function is the exit status of the last command, so no need for explicit return statements.
check_ls1() { # 这块可以有空格.
    [ -f "/bin/ls" ]
}
if  (check_ls1); then # or if check_ls1; then
    echo 文件存在
else
    echo 文件不存在
fi