#!/bin/bash

SCRIPT_DIR=$(dirname $(readlink -e $0))

mvn --also-make -f ${SCRIPT_DIR}/../pom.xml -pl 1DataStructureAndAlgorithm clean verify -Dskip.ut=false -Dskip.it=true