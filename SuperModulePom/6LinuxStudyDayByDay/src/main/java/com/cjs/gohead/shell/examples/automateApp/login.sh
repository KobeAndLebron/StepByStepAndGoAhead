#!/bin/bash

# Sometimes we may want to automate another program or script. If the other script expects user input,
# we may want to write a script to automatically fill in that information.
# First let's create a simple program that accepts a user name and password:

#Grab user name:
echo "user: "
read USER
echo ${USER}

#Grab password:
echo "pass: "
read PWD
echo $PWD

if [ "${USER}" == "test" ] && [ "${PWD}" == "test" ]; then
	echo "Login Success!"
else
	echo "Login Failed!"
fi