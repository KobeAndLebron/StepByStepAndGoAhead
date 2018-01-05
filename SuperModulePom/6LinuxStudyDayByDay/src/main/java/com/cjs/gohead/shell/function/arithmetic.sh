#!/bin/bash

### 四则运算


multipleCommand="expr 1 + 1"
multiple=`${multipleCommand}`
echo "${multipleCommand} = ${multiple}"

multipleCommand="echo "1 + 1" | bc -l"
multiple=`${multipleCommand}`
echo "${multipleCommand} = ${multiple}"


multipleCommand="1 + 1"
multiple=$(($multipleCommand))
echo "${multipleCommand} = ${multiple}"

## NOTE: 不能带引号
multiple=$((1 + 1))
echo "${multipleCommand} = ${multiple}"