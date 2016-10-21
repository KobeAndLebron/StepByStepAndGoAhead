#!/bin/bash

CURRENT_DIRECTORY=`pwd`
userName=`whoami`
directoryContainsK2db_rest=`find /home/${userName}/ -type d -name k2platform*k2db-rest 2>/dev/null` # ignore error output.
directory_rest=""

for directory in ${directoryContainsK2db_rest}; do
    cd ${directory} # temporarily enter the directory to execute mvn command.
    trueContent=`git status 2>/dev/null`
    if [ "$trueContent" != "" ]; then
        directory_rest=${directory}
        break
    fi
done

echo "The directory on which k2db-rest resides is ${directory_rest}, whether or not to continue(Y/N/y/n)? "
read IF_CONTINUE

if [ "${IF_CONTINUE}" == "y" ] || [ "${IF_CONTINUE}" == "Y" ]; then
    echo "Please if skip unit tests(true/false): "
    read IF_SKIP_UT
    mvn -f ../pom.xml -pl k2platform-k2db-rest -am clean install  -Dmaven.test.skip=${IF_SKIP_UT}
else
    echo "You does not execute mvn install command in directory named ${directory_rest}."
fi
