#!/bin/bash
# This program will read the filename from user input.

echo "Enter the file: "
read FILENAME
echo "Printing the head of ${FILENAME}.sh..."
head -n 1 ${FILENAME}.sh
echo ""

echo "Printing the tail of ${FILENAME}.sh..."
tail -n 1 ${FILENAME}.sh
echo ""