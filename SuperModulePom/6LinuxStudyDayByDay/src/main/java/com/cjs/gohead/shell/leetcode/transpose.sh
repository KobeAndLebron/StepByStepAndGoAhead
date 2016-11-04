#!/bin/bash
# Read from the file file.txt and print its transposed content to stdout.

loop=`head -n1 file.txt|awk '{print NF}'`
loop1=1
# echo "`cat file.txt | awk '{print \$'${loop}''`"
column=`cat file.txt | awk '{print $v1}' v1="$loop1"`
# echo ${column}
while [ ${loop1} -le ${loop} ]; do
    str=""
    for eleCol in ${column}; do
      str="${str} ${eleCol}"
    done
    # str="${str} "
    echo ${str}
    loop1=`expr ${loop1} + 1`
    column=`cat file.txt | awk '{print $v1}' v1="$loop1"`
done
#echo ${str}
