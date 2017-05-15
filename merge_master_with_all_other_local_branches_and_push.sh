#!/bin/sh

## The function of this sh(guarantee that (origin/)master is up-to-date with the current work branch):
## 1. Push the current branch to remote repository.
## 2. Merge the current branch to master.
## 3. Push the master branch to remote repository.

current_branch_name=`git branch -l | grep '^* [0-9a-zA-Z_]*$' | grep -o '[0-9a-zA-Z_]*$'`

#the branch which will be pushed
pushed_branch="master"
repositoryName=`git remote`
echo "The current branch is ${current_branch_name}\n"
#message_push=`git push` qq
#grep_message=`echo $message_push | grep -o '[0-9a-zA-Z]*...[0-9a-zA-Z]**'`
#echo "$grep_message"
git push
# Cannot ignore error output.
git checkout ${pushed_branch}  1>/dev/null
echo "1 Switch to $pushed_branch\n"

# Temporarily remove.
#allLocalBranches=`git branch -l | awk '{print $1}' | grep -v "*"`
#echo ${allLocalBranches}
#
#for branch in ${allLocalBranches}; do
#    if [ "${branch}" !=  "${pushed_branch}" ]; then
#        git merge ${branch} 1>/dev/null
#        echo "${branch} is merged to ${pushed_branch}"
#    fi
#done

git merge ${current_branch_name} 1>/dev/null
echo "${current_branch_name} is merged to ${pushed_branch}"

echo "2 Merge finish\n"
#message_push1=`git push | grep '-'`
#echo "$message_push1\n"
git push -u ${repositoryName} ${pushed_branch}:${pushed_branch}
echo "3 $pushed_branch is pushed to remote respority\n"
git checkout ${current_branch_name} 1>/dev/null 2>/dev/null
echo "4 switch to the previous branch: $current_branch_name\n"
