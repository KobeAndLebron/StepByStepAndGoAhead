#!/bin/bash

# loop by [ ]
var=1
while [ "${var}" -ne 10 ];
do
    echo -n "$var "
    var=$(expr ${var} + 1)
done
echo

#######################

# loop forever.
# way1.
while true;
do
    echo "a"
    break # 临时加, 防止死循环, 看不到前面允许的结果.
done
#way2
while :
do
    echo "b"
     break # 临时加, 防止死循环, 看不到前面允许的结果.
done