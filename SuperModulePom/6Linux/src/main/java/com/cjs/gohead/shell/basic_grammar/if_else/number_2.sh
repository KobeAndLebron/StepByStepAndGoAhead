#!/bin/bash
# prompt for a user name...
echo "Please enter you name:"
read USERNAME

# check for the file USERNAME
if [ -s ${USERNAME}_DAT ]; then # -s -> file exists and if not empty.
    # Read the age from the file.
    AGE=`cat ${USERNAME}_DAT` # Wrap a command with the character ` and this will put output of the command into variable ***
    echo "You are ${AGE} years old!"
else
    # Ask the user for his/her age
    echo "How old are you?"
    read AGE

    if [ ${AGE} -le 2 ]; then
        echo "You are too young!"
    else
        if [ "${AGE}" -ge 100 ]; then
            echo "You are too old!"
        else
            # Create a new file and write the age to the new file.
            echo ${AGE} > ${USERNAME}_DAT
        fi
    fi
fi

