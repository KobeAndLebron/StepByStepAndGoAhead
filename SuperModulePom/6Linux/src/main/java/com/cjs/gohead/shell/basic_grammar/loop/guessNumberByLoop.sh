#!/bin/bash

ANSWER=5    # The correct answer
CORRECT=false # The correct flag


while [ "$CORRECT" != "true" ]
do
    # Ask the user for the number...
    echo "Guess a number between 1 and 10. "
    read NUM

    # Validate the input...
    if [ "$NUM" -lt 1 ] || [ "$NUM" -gt 10 ]; then
        echo "The number must be between 1 and 10!"
    elif [ "$NUM" -eq "$ANSWER" ]; then
        echo "Success, You guess which number i am thinking of."
        CORRECT=true
    else
        echo "Sorry, please try again."
    fi
    echo ""
done
