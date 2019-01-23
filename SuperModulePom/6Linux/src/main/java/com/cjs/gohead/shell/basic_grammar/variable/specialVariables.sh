#!/bin/bash

### There are special shell variables which are set internally by the shell and which are available to the user.

# Expands to the positional parameters, starting from one.
# When the expansion occurs within double quotes, each parameter expands to a separate word.
echo $@

# Expands to the number of positional parameters in decimal.
echo $#

# Expands to the name of the shell or shell script
# (在哪个目录执行脚本, 就显示此脚本在哪个目录的相对路径, 即命令行输入的此脚本的名字, 例子: 输入$1/test.sh 则$0会代表"$1/test.sh").
echo $0 # 'readlink -e $0'可以得到文件的全路径.

# loop predefine variables.
for i in $@;
do
    echo -n "${i} "
done
echo
#区别: 上面不会按照"区分元素, 而下面会按照"区分元素. 例:./specialVariables.sh "1  2" 3,上面输出"1 2 3", 下面输出"1  2 3".
# https://unix.stackexchange.com/questions/41571/what-is-the-difference-between-and TODO
for i;
do
    echo -n "${i} "
done