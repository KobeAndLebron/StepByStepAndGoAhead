#!/bin/bash

# foreach predefine variables.
echo -n "Predefine variables: "
# '$#'表示预定于变量的长度, 类比数组'${array[@]}'是foreach数组的每一个元素, '${!array[@]}'是foreach by array index,
# '${#array[@]}'为数组的长度.
for i in "$@";
do
    echo -n "$i "
done
echo

######################

# foreach array.
array=("1" "2" 3 5)

# 与java中的foreach相似
# loop by element.
for ele in "${array[@]}";
do
    echo "Array length: ${#array[@]}; Ele ${ele}"
done

# loop by index
for index in "${!array[@]}" # 可不加';'
do
    echo "Index: ${index}, ele: ${array[index]}"
done

########################

# fori循环
for ((i=0; i<${#array[@]}; i++))
do
    echo ${array[$i]}
done


# fori嵌套循环
for (( i=0; i<${#array[@]}; i++));
do
    for (( j=0; j<${#array[@]}; j++));
    do
        echo ${array[$j]}
        echo `expr ${array[2]} + ${array[3]}`
    done
done


########################


# chessboard.
#for (( i = 1; i <= 9; i++ )) ### Outer for loop ###
#do
#   for (( j = 1 ; j <= 9; j++ )) ### Inner for loop ###
#   do
#        tot=`expr $i + $j`
#        tmp=`expr $tot % 2`
#        if [ $tmp -eq 0 ]; then
#            echo -e -n "\033[47m   "
#        else
#            echo -e -n "\033[40m   "
#        fi
#  done
# echo -e -n "\033[40m" #### set back background colour to black
# echo "" #### print the new line ###
#done
