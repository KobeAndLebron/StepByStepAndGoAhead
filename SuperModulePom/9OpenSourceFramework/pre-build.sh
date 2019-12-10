#!/bin/bash

SCRIPT_DIR=/Users/chenjingshuai/Codes/Java_workspace/StepByStepAndGoAhead/SuperModulePom/9OpenSourceFramework/

mvn --also-make -f ${SCRIPT_DIR}/../pom.xml -pl 9OpenSourceFramework clean verify -Dskip.ut=true -Dskip.it=true