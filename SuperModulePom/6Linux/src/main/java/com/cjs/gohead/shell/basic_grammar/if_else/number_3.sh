#!/bin/bash
# Test multiple expressions by using || operator or the && operator, this can save you form writing
# extra code to nest if statement(Compare with number_2.sh).

echo "Please input your age: "
read AGE

if ( ( [ "$AGE" -lt 20 ] ) || ( [ "$AGE" -ge 40 ] ) ); then
    echo "Sorry, you are out of the age range( (20, 40] )."
elif [ "$AGE" -ge 20 ] && [ "$AGE" -lt 30 ]; then # [20, 30)
    echo "You are in your 20s."
elif [ "$AGE" -ge 30 ] && [ "$AGE" -lt 40 ]; then # [30, 40)
    echo "You are in your 30s."
fi