#!/bin/bash
# This is some secure program that uses security.

VALID_PASSWORD="secret" #this is our password.

echo "Please enter the password: "
read INPUTTED_PASSWORD


# Spaces are very important when using an if statement!!!!
# Notice that the termination of the if statement is fi.
# The place of the "==" can put other tokens for other type tests.
if [ "$VALID_PASSWORD" == "$INPUTTED_PASSWORD" ]; then
    echo "You have access!"
else
    echo "ACCESS DENIED!"
fi

if [ ${VALID_PASSWORD} != "$INPUTTED_PASSWORD" ]; then
    echo ACCESS DENIED!
else
    echo You have access!
fi


