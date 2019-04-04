package com.cjs.goHead.mysql;

/**
 *   MySQL CASE expression is a flow control structure that allows you to construct conditions inside a query such
 * as SELECT or WHERE clause. MySQL provides you with two forms of the CASE expressions.
 *
 *
 *   FORM1:
         CASE
         WHEN condition_1 THEN result_1
         WHEN condition_2 THEN result_2
         …
         ELSE result END

    e.g1: UPDATE table1 set column = (CASE WHEN column = 'xxx' THEN 1 WHEN column = 'yyy' THEN 2 ELSE 0 END)
    e.g2: SELECT SUM(CASE WHEN column = 'xxx' THEN 1 ELSE 0 END) as columnStatus FROM table1.
 *
 *
 *   FORM2:
         CASE value
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

 *
 * Created by chenjingshuai on 19-4-4.
 */
public class CaseWhenClause {

}
