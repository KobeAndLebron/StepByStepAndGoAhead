#!/bin/bash

# Simplify ssh command.
echo "Type the username of host you want to access: "
read USERNAME

ipPattern="192.168.130"
echo "Use ${ipPattern}.** pattern for IP?(y/Y/n/N) "
read IF_USE_SPEC_PATTERN

# TODO 没有-q参数, 此命令有输出的时候, 就会报/myssh.sh: line 12: y: command not found; 加上ｑ则没有错误
# -q, --quiet, --silent
#  Quiet; do not write anything to standard output.  Exit immediately with zero status if any match is found,
#  even if an error was detected.  Also see the -s or --no-messages  option.   (-q
#  is specified by POSIX.)
$(echo "${IF_USE_SPEC_PATTERN}" | grep -Eiq  "^[y|Y]{1}$" -)

if [ $? -eq 0 ]; then
    echo "Use pattern."
    echo "Type the ip(最后几位) of host you want to enter: "
    read IP
    command="ssh ${USERNAME}@${ipPattern}.${IP}"
else
    echo "Do not use pattern."
    echo "Type the complete ip of host you want to enter: "
    read IP
    command="ssh ${USERNAME}@${IP}"
fi

echo "The ssh command to execute: ${command}"

# execute ssh command.
${command}
