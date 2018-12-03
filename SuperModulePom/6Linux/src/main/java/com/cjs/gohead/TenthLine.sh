#!/bin/bash
count1=0
while([ ${count1} -le 10 ]);do
    # count1=`expr ${count1} + 1`
    count1=$((${count1}+1))
    echo "aaa"
done;