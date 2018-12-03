#!/bin/bash

SCRIPT_DIR=$(dirname $(readlink -e $0))

mvn --also-make -f ${SCRIPT_DIR}/../pom.xml -pl 2YD_JavaAndSqlOptimizeStudyDayByDay clean verify -Dskip.ut=true -Dskip.it=false