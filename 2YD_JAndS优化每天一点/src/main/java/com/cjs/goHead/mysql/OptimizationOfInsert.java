package com.cjs.goHead.mysql;

/**
 * 
 * 1 一条SQL语句插入多条数据。
 * 	Insert into table 'insert_table' values (...);
 *  Insert into table 'insert_table' values (...);
 * 	Insert into table 'insert_table' values (...); ...
 * 	-------------->
 * 	Insert into table 'insert_table' values (...),(...),(...),...;
 * 	
 * 		通过合并SQL语句，减少了SQL语句解析的次数，减少了网络传输的I/O
 * 		减少了日志-binlog日志和innodb的事务让日志量，降低了日至刷盘的数据量和频率，从而提高了效率。
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年6月28日-上午7:59:07
 */
public class OptimizationOfInsert {

}
