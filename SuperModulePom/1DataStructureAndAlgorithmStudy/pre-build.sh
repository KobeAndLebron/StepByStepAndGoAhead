#!/bin/bash

SCRIPT_DIR=$(dirname $(readlink -e $0))
echo ${SCRIPT_DIR}
mvn --also-make -f ${SCRIPT_DIR}/../pom.xml -pl ../SuperModulePom/1DataStructureAndAlgorithmStudy clean verify -Dskip.ut=true -Dskip.it=true