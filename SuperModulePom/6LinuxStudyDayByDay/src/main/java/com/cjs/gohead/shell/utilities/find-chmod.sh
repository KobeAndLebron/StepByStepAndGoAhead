#!/bin/bash

directory=${1}
echo "The directory that find and chmod execute: ${directory}"
filePattern=${2}
echo "The pattern of file name: ${filePattern}"
operation=${3}
echo "The operation: ${operation}"

### Without the quotes(single or double), the shell will interpret *.sh as a glob pattern and expands it to any file names matching the
### glob before passing it to find command.This way, if you had, say, foo.java in the current directory, find's actual command line would be:
### find . -name foo.java
echo "The find command: find ${directory} -type f -name \"${filePattern}\""
directories=`find ${directory} -type f -name "${filePattern}"`

for eleFile in ${directories}; do
    chmodCommand="chmod ${operation} ${eleFile}"
    `${chmodCommand}`
    sub=`expr substr ${operation} 1 1`
    oper=`expr substr ${operation} 2 1`
    obj=`expr substr ${operation} 3 1`
    echo "Execute ${sub} ${oper} ${obj} on ${eleFile}"
done
