#!/bin/sh

# The script will show all users that are in the file and then display how many files that user accessed.
# It will also show all files in the file and then display how many times each file was displayed.

# The file is 'access.text' that is in the directory in which the script is.

file="access.text"
# get all unique users in the file.
users=`gawk '{print $1}' ${file} | sort -u`
echo "Users: "

# traversal users and get how many files each of them accessed.
for user in ${users}; do
    # ^ means that strings matched start with $user.
    count=`grep -c "^${user}" ${file}`
    echo " - ${user}: ${count} file(s) accessed."
done


files=`gawk '{print $4}' ${file} | sort -u`
echo "Files: "

for eleFile in ${files}; do
    count=`grep -c "${eleFile}$" ${file}`
    echo " - ${eleFile}: ${count} access(es)."
done