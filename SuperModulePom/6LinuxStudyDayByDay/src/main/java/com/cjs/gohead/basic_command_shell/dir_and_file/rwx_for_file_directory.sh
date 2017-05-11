#!/bin/bash

# Linux将文件的存取身份划分为owner group others, 且各种身份都有read write execute等权限.

# 目录和文件的权限意义
#
# 1、权限对文件的重要性
#
### r(read)：读取文件内容, 如读取文本的文字内容等
### w(write)：可以编辑、新增、修改文件的内容（但不含删除该文件）. cp文件的时候就需要r权限.
### x(execute)：具有被系统执行的权限。注意：在Linux中, 文件是否能否被执行是由文件是否具有x权限决定的, 与文件名的扩展名无关。
# 2、权限对目录的重要性
#
### r(read contents in directory)：具有读取目录结构列表的权限(即ls), 与用户能否进入该目录无关(不能进入则显示乱码)。
### w(modify contents of directory)：具有更改该目录结构列表的权限, 包括新建文件或目录、删除文件和目录（不论该文件权限如何）
###  、对文件或目录进行重命名、转移文件或目录位置(必须有x权限才能工作). e.g. cp的时候既要有源文件的r权限, 也要有此目录的w权限. 才能成功.
### x(execute directory)：具有进入该目录的权限, 使该目录成为工作目录, 即目前所在的目录。
### 如果没有x权限, 即使有r权限, 也无法切换到该目录执行该目录下的命令。
lsWithSudo='sudo ls -al /root'
lsWithoutSudo='ls -al /root'
echo '将/root的others权限设置为空...'
sudo chmod o= /root

echo "执行${lsWithSudo}"
${lsWithSudo}

echo "执行${lsWithoutSudo}, 由于没有r权限所以Permission denied."
${lsWithoutSudo}

i=1
echo "countDown ${i} seconds."
while [ "${i}" -gt 0 ]
do
    sleep 1
    i=`expr ${i} - 1`
done

echo '设置/root的others权限为r...'
sudo chmod o=r /root
ls -al /root #由于没有x权限所以不能进入, 显示的文件目录乱码
cd /root # Permission denied.

echo '设置/root的others权限为x...'
sudo chmod o=x /root
ls -al /root # Permission denied, 必须有r权限.
cd /root && ls -al ./ # Former allowed, the latter denied.
sudo chmod o+r /root
cd /root && ls -al ./ # Well done.
rm -r .cache # Fail, do not have w permission.

# sudo ls -al /root/
# d-wx---r-x 13 root root  4096  5月  5 15:08 ./  # others对当前目录有rx权限.
# -rw-------  1 root root  6207  5月  4 11:48 .viminfo # others对当前文件没有任何权限.
#### 对于others来说,
# cp -p .viminfo .viminfo_copy
# (1. 由于对此目录有可读的权限, 所以此文件名可自动补全
# 2. 对此文件没有r权限, 所以cp: cannot open ‘.viminfo’ for reading: Permission denied )
# 给.viminfo赋予r权限, 再次执行cp -p .viminfo .viminfo_copy
# 接下来要创建.viminfo_copy这个, 需要有此目录的w权限, 所以由于others对此目录没有w权限, 出现
# cp: cannot create regular file ‘.copy’: Permission denied
# 给当前目录附上w权限, 则cp执行成功.

### Note, root只有wx权限的时候, 上述cp也能顺利执行(在对文件有r权限的前提下),
### 少了r权限只是不知道目录下有哪些文件而已, 即文件名不能自动补全