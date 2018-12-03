#!/bin/bash

# First solution
awk '{if(FNR == 10) print $0; }' tenthLine.txt
echo 'The result of the first solution finish.'

# Second solution
cnt=1
outPutLine=""
while read line && [ ${cnt} -le 10 ]; do
    cnt=`expr ${cnt} + 1`
    outPutLine=${line}
done < tenthLine.txt
echo ${outPutLine}
