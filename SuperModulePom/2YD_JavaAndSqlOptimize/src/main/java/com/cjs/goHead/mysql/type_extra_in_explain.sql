-- 使用explain来分析sql语句的执行计划, 这里解释explain返回结果中的type和extra类.

-- type：检索数据的方式【从左到右效率依次降低】
-- -- system> const> eq_ref[唯一索引 主键索引的单值查询]> ref[索引树的单值查询] > range[索引树范围查询] > index[全索引数扫描] > all[全表扫描]
-- Extra：
-- -- Using index：使用到覆盖索引， 没有回表， 不管索引有没有生效。 | 没有代表回表了。
-- -- Using where：Where条件索引失效（部分或所有）或无索引，表示在经过存储引擎过滤后，仍要在服务器层进行过滤。 | 没有代表索引全部生效或无where条件, 即不在服务器层在进行过滤.
-- -- Using index condition: 针对组合索引, 索引部分失效后, 会直接在存储引擎层过滤完数据，返回给服务层，不在服务层进行过滤【5.7之后才有】
-- -- Using index + Using where：使用到覆盖索引，但是索引失效，仍要在服务器层进行过滤。
-- -- Using filesort： MYSQL服务器无法利用索引完成的排序操作，称为文件排序。

CREATE TABLE IF not exists `test_index`
(
    id                              BIGINT,
    single_index                    VARCHAR(255), -- 单列索引
    single_index_on_alter_table     VARCHAR(255),
    multiple_index_1                VARCHAR(255), -- 联合索引
    multiple_index_2                VARCHAR(255),
    multiple_index_3                VARCHAR(255),
    multiple_index_1_on_alter_table VARCHAR(255),
    multiple_index_2_on_alter_table VARCHAR(255),
    PRIMARY KEY (id),
    KEY (single_index),
    KEY (multiple_index_1, multiple_index_2, multiple_index_3)
) ENGINE = innodb
  default charset = utf8;

-- -- 建立索引的两个语法:
-- 单列索引
ALTER TABLE `test_index`
    ADD INDEX single_index_on_alter_table (single_index_on_alter_table);
-- 多列索引
CREATE INDEX multiple_index_on_alter_table ON `test_index` (multiple_index_1_on_alter_table, multiple_index_2_on_alter_table);

-- 开始分析explain.

-- -- -- 索引全部生效 -- -- -- 无Using where.
-- 索引生效；type: ref【索引树上的单值查询】， extra: null【未能使用覆盖索引】，key_len：768【第一列被使用】。
EXPLAIN SELECT *
        from test_index
        where multiple_index_1 = '1';

-- 索引生效；type: ref， extra: Using index【使用到覆盖索引】，key_len：1536【前两列被使用】。
EXPLAIN SELECT multiple_index_1, multiple_index_2, multiple_index_3
        from test_index
        where multiple_index_1 = '1'
          and multiple_index_2 = '2';

-- 索引生效；type: ref， extra: null【未能使用覆盖索引】, key_len：2304【三列全部被使用】。
EXPLAIN SELECT *
        from test_index
        where multiple_index_1 = '1'
          and multiple_index_2 = '2'
          and multiple_index_3 = '3';
-- -- -- 索引全部生效 -- -- -- 无Using where.

-- -- -- 跨列使用会使后边的所有索引都失效。
-- 1. 索引失效，跨列使用会使后面的索引都失效。且查询的列包含非索引列，不是覆盖索引。
-- type: ALL，全表扫描。 extra：Using where， 索引失效。
EXPLAIN SELECT *
        from test_index
        where multiple_index_2 = '2'
          and multiple_index_3 = '3';
-- 索引失效，但是查询的列都在聚集索引内，可以使用覆盖索引。
-- type：index，全索引树扫描。 extra: Using where; Using index。
EXPLAIN SELECT multiple_index_1
        from test_index
        where multiple_index_2 = '2'
          and multiple_index_3 = '3';

-- ICP（index condition pushdown): 表示索引失效后, 会在存储引擎层直接回表, 将整条记录返回给服务器层, 然后服务器层在进行过滤, 减少了服务器访问存储引擎的次数, 从而提高效率.
-- ICP仅在使用索引存在且生效的情况下生效.
set optimizer_switch = 'index_condition_pushdown=on';
-- 索引失效，仅仅第一个索引列生效【ken_len为768】，type为ref，
-- extra： Using index condition[等价于Using Where+Non-Using index]， 即没有使用覆盖索引且索引失效。
EXPLAIN SELECT *
        from test_index
        where multiple_index_1 = '2'
          and multiple_index_3 = '3';
-- 索引全部失效, ICP失效, Extra: Using where.
EXPLAIN SELECT *
        from test_index
          where multiple_index_3 = '3';

-- 关闭ICP.
set optimizer_switch = 'index_condition_pushdown=off';
-- 仅仅第一个索引列生效【ken_len为768】，type为range，extra: Using where。
EXPLAIN SELECT *
        from test_index
        where multiple_index_1 = '2'
          and multiple_index_3 = '3';

-- -- -- 跨列使用会使后边的所有索引都失效。


-- -- -- like查询使索引失效。
-- 2. 索引失效。
-- type: all【全表扫描】, extra: Using where。
EXPLAIN SELECT *
        from test_index
        where multiple_index_1 like '%1%';
-- 索引失效。
-- type：index【全索引数扫描】, extra: Using where; Using index。
EXPLAIN SELECT multiple_index_3
        from test_index
        where multiple_index_1 like '%1%'
          and multiple_index_2 = '1';
-- 前两列索引生效【ken_len为1536】。
EXPLAIN SELECT *
        from test_index
        where multiple_index_1 like '1%'
          and multiple_index_2 = '3';
-- -- -- like查询使索引失效。

-- 3. 索引失效，原因：字符串不加单引号，MYSQL做了类型转换。 type: all.
EXPLAIN SELECT *
        from test_index
        where multiple_index_1 = 1;
-- 4. 索引失效，原因：使用`or`连接了索引列。 type：index.
EXPLAIN SELECT multiple_index_1
        from test_index
        where multiple_index_1 = '1'
           or multiple_index_2 = '1';
-- 5. 索引失效，原因：使用了'<>''!='不等于号。 type：all.
EXPLAIN SELECT *
        from test_index
        where multiple_index_1 <> '1'
          and multiple_index_2 = '1';
-- 6. 索引失效，原因：使用了in操作符。 type：all.
EXPLAIN SELECT *
        from test_index
        where multiple_index_1 in ('1', '2')
          and multiple_index_2 = '1';
-- 7. 索引失效，原因：索引列参与了运算。
-- ype: all. Extra: Using where.
EXPLAIN SELECT *
        from test_index
        where concat(multiple_index_1) = '1';