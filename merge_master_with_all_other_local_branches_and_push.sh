#!/bin/sh
current_branch_name=`git branch -l | grep '^* [0-9a-zA-Z]*$' | grep -o '[0-9a-zA-Z]*$'`
#the branch which will be pushed
pushed_branch="master"
echo "The current branch is $current_branch_name"
git checkout $pushed_branch
echo "1 Switch $pushed_branch"
git merge 450 1>/dev/null
git merge 480 1>/dev/null
git merge dell 1>/dev/null
echo "2 Merge finish"
git push 1>/dev/null
echo "3 $pushed_branch is pushed to remote respority"
git checkout $current_branch_name 1>/dev/null
echo "4 switch $current_branch_name" 
