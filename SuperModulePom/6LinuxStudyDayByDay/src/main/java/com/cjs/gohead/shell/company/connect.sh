#!/bin/sh

# Use predefined variable to simplify ssh command(In the case that most portion of ip is certain).
command="ssh ${1}@192.168.130.${2}"
echo ${command}
`${command}`
