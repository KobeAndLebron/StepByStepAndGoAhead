#!/bin/bash
# This program will print the head and tail of a file passed in on the command line.

echo "Printing head of ${1}..."
head -n 1 ${1}

echo "" #This will print an extra return...
echo "Printing tail of ${1}..."
tail -n 1 ${1}
echo ""