#!/bin/sh

# Now we need to create a script to automate this script(login.sh). To do this, all we need to do is output the user name followed
# by the password to the command line, we will pass these as two parameters:

USER=$1
PWD=$2

echo ${USER}
echo ${PWD}

# Now to run this automation script, we simply need to pipe the output to the login.sh script.
# like this: ./auto.sh test test | ./login.sh