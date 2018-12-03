test命令和[是同义词, 即以下两条命令是等价的:  
> `[ -x /bin/ls ]`  
> `test -x /bin/ls`
  
　　都是检测文件是否存在且执行权限被赋予, 如果是则返回0否则1.

## 例子1
```shell
check_ls(){
    if [ -f "/bin/ls" ]
    then
         return 1
    else
         return 0
    fi
}

## 等价于 test check_ls, 等价于 test -n check_ls.
## 
if [ check_ls ]; then 
    echo 文件存在
else
    echo 文件不存在
fi
```
上面脚本会一直输出"文件存在".  
正确写法:
```shell
check_ls(){
    if [ -f "/bin/ls" ]
    then
         return 0
    else
         return 1
    fi
}

if  check_ls; then 
    echo 文件存在
else
    echo 文件不存在
fi
```
check_ls()函数可以简写为:
```shell
check_ls(){
    [ -f "/bin/ls" ]
}
```
> Reference: test OPTION -n   
> -n STRING     
>       the length of STRING is nonzero  
>       STRING equivalent to -n STRING  
> -x File  
->      FILE exists and execute (or search) permission is granted  
> -s FILE  
> FILE exists and has a size greater than zero


## Others
1. [ 1 < 8 ], 报错: `bash: 8: No such file or directory`. 因为[是一个命令, 所以会把<解释为重定向输出流.
   正确写法: [ 1 \\< 8]


2. test's string equality operator doesn't do globs.
```shell
$ [ abc = *bc ] ; echo $?
1
$ [[ abc = *bc ]] ; echo $?
0
```

3. [Why is `[` a shell builtin and `[[` a shell keyword?](https://unix.stackexchange.com/questions/183745/why-is-a-shell-builtin-and-a-shell-keyword/183749#183749)
> Summary: [是一个命令, 会像其他命令一样对参数进行分割和文件名扩展参数, 所以需要对参数加上双引号防止这种操作. 否则可能会出现错误: `too many arguments` `unary operator expected`  `binary operator expected`. 一元/二元操作符.   
> [[是一个关键字, 所以不需要加双引号, 除了1中的区别, 还有[这个区别](http://mywiki.wooledge.org/BashFAQ/031).