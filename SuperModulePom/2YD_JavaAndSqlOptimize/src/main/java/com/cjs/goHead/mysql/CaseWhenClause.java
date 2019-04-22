package com.cjs.goHead.mysql;

/**
 *   MySQL CASE expression is a flow control structure that allows you to construct conditions inside a query such
 * as SELECT or WHERE clause. MySQL provides you with two forms of the CASE expressions.
 *
 *
 *   FORM1(each WHEN clause search_condition expression is evaluated until one is true, at which point
 *   its corresponding THEN clause statement_list executes. If no search_condition is equal, the ELSE
 *   clause statement_list executes, if there is one.):
         CASE
         WHEN condition_1 THEN result_1
         WHEN condition_2 THEN result_2
         …
         ELSE result END

    e.g1: UPDATE table1 set column = (CASE WHEN column = 'xxx' THEN 1 WHEN column = 'yyy' THEN 2 ELSE 0 END)
    e.g2: SELECT SUM(CASE WHEN column = 'xxx' THEN 1 ELSE 0 END) as columnStatus FROM table1.
    e.g3(没有ELSE): SELECT (CASE student = 'I' WHEN 0 THEN 1 END) FROM courses;

 *
 *
 *   FORM2(case_value is an expression. This value is compared to the when_value expression
 *   in each WHEN clause until one of them is equal. When an equal when_value is found, the
 *   corresponding THEN clause statement_list executes. If no when_value is equal, the ELSE
 *   clause statement_list executes, if there is one.):
         CASE case_value
         WHEN compare_value_1 THEN result_1
         WHEN compare_value_2 THEN result_2
         …
         ELSE result END
    e.g..
         mysql> -- If all conditions are false, then the result in the ELSE part is returned.
         mysql> -- If the ELSE part is omitted, the CASE expression returns NULL .
         mysql> SELECT (CASE 1 WHEN 0 THEN 2 END);
         +----------------------------+
         | (CASE 1 WHEN 0 THEN 2 END) |
         +----------------------------+
         |                       NULL |
         +----------------------------+
         1 row in set (0.02 sec)

         mysql> SELECT (CASE 1 WHEN 0 THEN 2 ELSE 1 END);
         +-----------------------------------+
         | (CASE 1 WHEN 0 THEN 2 ELSE 1 END) |
         +-----------------------------------+
         |                                 1 |
         +-----------------------------------+

         SELECT (CASE student WHEN 'I' THEN 1 END) FROM courses; (同上述例子3)


     IFNULL function:  MySQL IFNULL function is one of the MySQL control flow functions that accepts two arguments and
   returns the first argument if it is not NULL. Otherwise, the IFNULL function returns the second argument.

     e.g1: SELECT IFNULL(1,0); -- returns 1
     eg2:  SELECT IFNULL('',1); -- returns ''
     eg3:  SELECT IFNULL(NULL,'IFNULL function'); -- returns IFNULL function

 *
 * Created by chenjingshuai on 19-4-4.
 */
public class CaseWhenClause {

}
