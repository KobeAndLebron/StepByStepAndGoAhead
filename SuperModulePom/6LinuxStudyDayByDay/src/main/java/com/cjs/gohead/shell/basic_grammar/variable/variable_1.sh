#!/bin/bash
# This is a script that introduce variable in script

# Variable name has the same convention with java
# There must be no space between the variable name and the equal sign/the equal sign and the variable value.
var_1="Hello"
var_2="world"

# To use the variable,we simply put a dollar sign "$" before the name of the variable in our script.

echo "${var_1}and{$var_2}and$var_1" #${} just outputs value of variable and {$va} outputs {value of variable}
#Helloand{world}andHello
