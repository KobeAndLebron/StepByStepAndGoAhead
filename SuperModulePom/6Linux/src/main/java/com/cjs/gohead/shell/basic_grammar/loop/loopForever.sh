#!/bin/bash

# The program will print out how long it has been running until the user presses CTRL+C
# to terminate the program.

COUNTER=0

while :
do
    sleep 1
    COUNTER=`expr ${COUNTER} + 1` # The expr command is used to evaluate a mathematical expression.
    echo "Program has been running for $COUNTER seconds..."
done