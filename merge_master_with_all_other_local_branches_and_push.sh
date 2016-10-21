#!/bin/sh
current_branch_name=`git branch -l | grep '^* [0-9a-zA-Z_]*$' | grep -o '[0-9a-zA-Z_]*$'`
#the branch which will be pushed
pushed_branch="master"
respositoryName=`git remote`
echo "The current branch is ${current_branch_name}\n"
#message_push=`git push` qq
#grep_message=`echo $message_push | grep -o '[0-9a-zA-Z]*...[0-9a-zA-Z]**'`
#echo "$grep_message"
git push 
git checkout $pushed_branch  1>/dev/null 2>/dev/null
echo "1 Switch $pushed_branch\n"

allLocalBranches=`git branch`
echo ${allLocalBranches}  # fixme wrong results.
for branch in ${allLocalBranches}; do
    if [ "${branch}" !=  "${current_branch_name}" ]; then
        echo "merge ${branch}"
        git merge ${branch} 1>/dev/null
    fi
done
echo "2 Merge finish\n"
#message_push1=`git push | grep '-'`
#echo "$message_push1\n"
git push -u ${respositoryName} $pushed_branch:$pushed_branch
echo "3 $pushed_branch is pushed to remote respority\n"
git checkout $current_branch_name 1>/dev/null 2>/dev/null
echo "4 switch $current_branch_name\n" 
