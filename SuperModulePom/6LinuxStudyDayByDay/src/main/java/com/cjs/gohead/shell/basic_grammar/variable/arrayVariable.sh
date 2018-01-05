#!/bin/bash

array=("1" "2" 3 5)

echo "输出所有数据\${array[@]}: ${array[@]}"
echo "输出数组最后一个位置的数据\${array[-1]}: ${array[-1]}"
echo "输出数组的长度\${#array[@]}: ${#array[@]}"

# 数组赋值
arrCopy=("${array[@]}")
echo "${arrCopy[@]}"

# 空数组
emptyArr=()
echo ${#emptyArr[@]}
# 循环数组
# Refer to ${PROJECT_PATH}/StepByStepAndGoAhead/SuperModulePom/6LinuxStudyDayByDay/src/main/java/com/cjs/gohead/shell/basic_grammar/loop/forLoop.sh