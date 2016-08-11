package com.cjs.gohead;

/**
 * Git stash:
 * 	Stash the changes in a dirty working directory away(included working directory and index).
 * 
 * Description:
 * 	Use git stash when you want to record the current state of the working directory and the index,but you want to go back to a clean working directory.
 * The command saves your local modifications away and reverts the working directory to match the HEAD commit.
 * 
 *  The changes stashed away by this command can be listed with git stash list,and inspected with git stash show,and restored(potentially on top of a 
 * different commit)with git stash apply.Calling git stash without any arguments is equivalent to git stash save.A stash is by default listed as "WIP
 * on branchname ...",but you can give a more descriptive message on the command line when you create one.
 * 
 * Options	
 *  save [-k|--[no-]keep-index][-u|--include-untracked][<message>]
 *   For quickly making a snapshot,you can omit both save and message,but giving only message does not trigger this action to prevent a misspelled 
 *  subcommand from making an unwanted stash.
 *   If the --keep-index option is used,all changes already added to the index are left intact.Default behaviour is --no-keep-index
 *   If the --include-untracked option is used,all untracked files are also stashed and then cleaned up with git clean,leaving the working directory in a 
 *  very clean state.
 * 	
 * 	show [<stash>] [-p|-stat] 
 *   Default behaviour is --stat
 *   
 *  apply [--index] [<stash>]
 *   If the --index option is used,then tries to reinstate not only the working tree's changes,but also the index's ones.(Possibly conflict with changes
 *  have added to the index and then fails.)Default behaviour is no --index argument.
 *  
 *  clear 
 *   Remove all stashed changes.
 *   
 *  Some use scenarios: UseScenariosOfGitStash.png
 *  Some examples:	src/main/resources/git-stash/...
 *  	
 *  
 *  Just study some basic operations of git stash and there are also some arguments which not listed.
 *  
 *  TODO The relation of git status command and git diff command.
 * @author ChenJingShuai 11 Aug 2016
 *
 */
public class B2016_8_11_git_stash {

}
