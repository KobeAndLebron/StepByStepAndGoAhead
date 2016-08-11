package com.cjs.gohead;

/**
 * 	The permission in the command line is displayed as:_rwxrwxrwx 1 owner:group
 * 1.User rights/permissions
 * 	1.The first character that marked with underscore is the special permission flag that can vary.
 * 			_-no special permissions 
 * 			d-directory
 * 			l-the file or directory is a symbolic link
 *  2.The following set of three characters(rwx) is for the owner permissions.
 *  3.The second set of three characters(rwx) is for the group permissions.
 *  4.The third set of three characters(rwx) is for the the all users permissions.
 * 2.Following that grouping since the integer/numbers displays the number of hardlinks to the file.
 * 3.The last piece is the owner and group assignment formatted as owner:group
 * 	
 *  r-read binary of representation 4
 *  w-write  ...............		2
 *  x-execute ..............  		1
 *  and 0 represents no permissions
 *  
 *  Usage: chmod [option] mode[,mode]... file
 *  	   chmod --reference=file1 file2 ---> make permission for a file same as another file(--reference) 
 *  	option: -c --changes like verbose but report only when a change is made
 *  			-v --verbose output a diagnostic for every file processed
 *   			-R --recursive change files and directories recursively
 *   				--preserve-root fail to operate recursively on '/'
 * 					--no-preserve-root do not treat '/' specially(the default case)
 * 					--version output the version information and exit
 * 		each mode is of the form '[ugoa]*([-+=]([rwxXst]*|[ugo]))+ | [-+=][0-7]+'
 * 		数字和u=不能一起用(矛盾),一个数字代表给a授权,两个数字代表给g和a授权,三个数字代表给u g和a授权
 * 	Examples: chmod 7 file == chmod rwx file 给所有用户赋rwx权限   4+2+1 = 7
 * 			  chmod 777 file == chmod rwxrwxrwfx file 
 *   		  chmod ugo=x file 重新给所有人分配file的可执行权限  		  
 *   		
 *   
 * @author ChenJingShuai  
 *
 * Make a bit of progress every day. 2016年7月13日-下午4:56:17
 *
 */
public class Command_7_13_chmod {

}
