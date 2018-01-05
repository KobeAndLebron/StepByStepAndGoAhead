#!/bin/bash

zookeeper=$1
consumerGroup=$2
topic=$3
intervals=$4
prevOffsetArr=()

function monitor(){
    offsetResultInfo=$(bin/kafka-run-class.sh kafka.tools.ConsumerOffsetChecker --zookeeper ${zookeeper} --group ${consumerGroup} \
                --topic ${topic})
    # Harvest Preserve formatting when command output is sent to a variable?
    testForQuestion=${offsetResultInfo}
    echo "$testForQuestion"

    offset=$(echo "$offsetResultInfo" | awk '{ print $4; }')

    read -d -r -a currOffsetArr <<< "${offset}"
    echo "currOffsetArr=${currOffsetArr[@]}"
    echo "prevOffsetArr=${prevOffsetArr[@]}"

    if [ ${#prevOffsetArr[@]} == 0 ]; then
         prevOffsetArr=( "${currOffsetArr[@]}" )
    else
        for (( i = 1; i < ${#currOffsetArr[@]}; i++ ))
        do
            gap=`expr ${currOffsetArr[i]} - ${prevOffsetArr[i]}`
            speed=`expr ${gap} / ${intervals}`
            echo ${speed}
        done

        prevOffsetArr=( "${currOffsetArr[@]}" )
    fi

}

while [ true ]
do
    monitor
    sleep ${intervals}
done
