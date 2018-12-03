#!/bin/bash

SCRIPT_DIR=$(dirname $(readlink -e $0))

mvn --also-make -f ${SCRIPT_DIR}/../pom.xml -pl 9OpenSourceFramework clean verify -Dskip.ut=true -Dskip.it=true