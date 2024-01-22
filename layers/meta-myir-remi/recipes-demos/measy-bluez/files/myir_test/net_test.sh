#!/bin/bash

RESULT_FILE=/var/wifi_result.txt
if [ "$#" -ne 2 ]; then
    echo "Usage: \$0 <ethx> <IP>"
    exit 1
fi

ethx=$1
ip=$2
gateway=${ip%.*}.1
ping_result=`ping ${gateway} -I ${ethx} -c 5 | grep packets`

ping_recive_num=`echo ${ping_result} | awk '{print $4}'`
if [[ ${ping_recive_num} -ge 1 ]];then
    echo -n "ping sucess ${ping_recive_num}" > ${RESULT_FILE}
    echo -n "ping sucess ${ping_recive_num}"
    sync
    exit 0
else
    echo -n "ping fail ${ping_result}" > ${RESULT_FILE}
    echo -n "ping fail ${ping_result}"
    sync
    exit 1
fi