#!/bin/bash

string="1,2,3,4,5"
IFS="," read -r -a array <<< "$string"
echo "${array[@]}"

string="1  2 3 4 5"
read -r -a array <<< "$string"
# 等价于
#　IFS=" " read -r -a array <<< "$string"
echo "${array[@]}"


string="1
2
3
4
5"
# TODO Split by 空行
IFS='/^$/' read -r -a array <<< "$string"
echo "${array[@]}"