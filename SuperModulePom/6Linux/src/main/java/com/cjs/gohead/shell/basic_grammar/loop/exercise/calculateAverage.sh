#!/bin/bash

# 计算平均数, 不支负数.
sum=0
count=0
while :
do
  echo -n "Enter your score [0-xxxx] ('q' for quit):"
  read var
  if [ ${var} == "q" ];then
    average=$(expr ${sum} / ${count})
    echo "Sum: $sum, Count: $count, Average: $average"
    break
  elif (($var < 0));then
    echo "Do not support negative."
  else
    sum=$(expr ${sum} + ${var})
    count=$[$count + 1]
  fi
done
