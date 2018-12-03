# [有道云笔记-AWK命令](http://note.youdao.com/noteshare?id=97df29550065bb7ec9ddbe636ac99ccb&sub=DA34969C98814C7B867CBA4334F13C23)

# 案例:
1. echo "aa bb cc" | awk '//' 
2. echo "aa bb cc" | awk ''  
3. echo "aa bb cc" | awk '//{}'  
4.    
    a. echo "aa,bb,cc" | awk '//{print $0}'  
    b. echo "aa,bb,cc" | awk 'BEGIN {FS=","} '  
    c. echo "aa,bb,cc" | awk 'BEGIN {FS=","} {print $1 " and " $2}'
    d. echo "aa,bb,cc" | awk 'BEGIN {RS=","} {print $1}'  
上述案例对应结果:  
1. 原始内容, 默认action为print all lines simply(print $0).  
2. search and action portion are not given, 不输出任何东西.  
3. 不输出任何东西.  
4.   
   a. 输出原始内容. $0代表输出整个行.  
   b. 不输出任何内容, 理由同2.  
   c. 输出: aa and bb.  
   d. 输出aa \n bb \n cc \n   

# Leetcode问题:
```java
How would you print just the 10th line of a file?
For example, assume that file.txt has the following content:

Line 1
Line 2
Line 3
Line 4
Line 5
Line 6
Line 7
Line 8
Line 9
Line 10
Your script should output the tenth line, which is:

Line 10
``` 
Answer:   
`awk '{if ( FNR == 10 ) { print $0;} else {} }' file.txt`
