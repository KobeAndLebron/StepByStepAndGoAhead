#!/bin/bash

# offsetInfo的例子:
#Group           Topic                          Pid Offset          logSize         Lag             Owner
#goldwind3s      gw_3s                          0   5161760         12160863        6999103         goldwind3s_kmx-10-1516674483979-4a121947-0
#goldwind3s      gw_3s                          1   5150592         12160921        7010329         goldwind3s_kmx-10-1516674483979-4a121947-0
#goldwind3s      gw_3s                          2   5138384         12162538        7024154         goldwind3s_kmx-10-1516674486544-28669f05-0
#goldwind3s      gw_3s                          3   5128849         12157205        7028356         goldwind3s_kmx-10-1516674486544-28669f05-0
#goldwind3s      gw_3s                          4   5054238         12158807        7104569         goldwind3s_kmx-10-1516674491225-d5369abb-0
#goldwind3s      gw_3s                          5   5058567         12160011        7101444         goldwind3s_kmx-10-1516674491225-d5369abb-0
#goldwind3s      gw_3s                          6   5137035         12166905        7029870         goldwind3s_kmx-11-1516674483481-4168830d-0
#goldwind3s      gw_3s                          7   5149116         12171166        7022050         goldwind3s_kmx-11-1516674483481-4168830d-0
#goldwind3s      gw_3s                          8   5092134         12161891        7069757         goldwind3s_kmx-11-1516674488378-dafd9876-0
#goldwind3s      gw_3s                          9   5099792         12165085        7065293         goldwind3s_kmx-11-1516674488378-dafd9876-0
#goldwind3s      gw_3s                          10  5087450         12159651        7072201         goldwind3s_kmx-11-1516674493093-494b0b91-0
#goldwind3s      gw_3s                          11  5108584         12167026        7058442         goldwind3s_kmx-11-1516674493093-494b0b91-0
#goldwind3s      gw_3s                          12  4949058         12162702        7213644         goldwind3s_kmx-5-1516674455438-4faabbfa-0
#goldwind3s      gw_3s                          13  4928822         12168390        7239568         goldwind3s_kmx-5-1516674455438-4faabbfa-0

zookeeper=$1
consumerGroup=$2
topic=$3
intervals=$4
prevOffsetArr=()
preProducerOffsetArr=()

function monitor(){
    offsetResultInfo=$(bin/kafka-run-class.sh kafka.tools.ConsumerOffsetChecker --zookeeper ${zookeeper} --group ${consumerGroup} \
                --topic ${topic})
    # Harvest Preserve formatting when command output is sent to a variable?
    testForQuestion=${offsetResultInfo}
    echo "$testForQuestion"

    offset=$(echo "$offsetResultInfo" | awk '{ print $4; }')
    # producer的offset信息.
    # 与consumer的offset信息数组大小相同(见最顶上例子).
    producerOffset=$(echo "$offsetResultInfo" | awk '{ print $5; }')

    read -d -r -a currOffsetArr <<< "${offset}"
    echo "currOffsetArr=${currOffsetArr[@]}"
    echo "prevOffsetArr=${prevOffsetArr[@]}"

    read -d -r -a currProducerOffsetArr <<< "${producerOffset}"
    echo "currProducerOffsetArr=${currProducerOffsetArr[@]}"
    echo "preProducerOffsetArr=${preProducerOffsetArr[@]}"

    if [ ${#prevOffsetArr[@]} == 0 ]; then # 第一次获取offset信息.
         prevOffsetArr=( "${currOffsetArr[@]}" )
         preProducerOffsetArr=( "${currProducerOffsetArr[@]}" )
    else
        for (( i = 1; i < ${#currOffsetArr[@]}; i++ ))
        do
            gap=`expr ${currOffsetArr[i]} - ${prevOffsetArr[i]}`
            speed=`expr ${gap} / ${intervals}`

            producerGap=`expr ${currProducerOffsetArr[i]} - ${preProducerOffsetArr[i]}`
            producerSpeed=`expr ${producerGap} / ${intervals}`

            isConsumerSpeedBiggerThanProducer=false
            if [ ${speed} -ge ${producerSpeed} ];then
                isConsumerSpeedBiggerThanProducer=true
            fi

            echo "Pid: $((i - 1));   ConsumerSpeed: ${speed}/s;   ProducerSpeed:  ${producerSpeed}/s;   \
            ConsumerSpeed >= ProducerSpeed ?  ${isConsumerSpeedBiggerThanProducer}."
        done

        prevOffsetArr=( "${currOffsetArr[@]}" )
        preProducerOffsetArr=( "${currProducerOffsetArr[@]}" )
    fi

}

while [ true ]
do
    monitor
    sleep ${intervals}
done
