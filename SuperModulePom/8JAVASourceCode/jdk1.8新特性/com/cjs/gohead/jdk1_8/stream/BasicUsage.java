package com.cjs.gohead.jdk1_8.stream;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * 流的操作类型分为:
 *  1. 中间操作(intermediate operation)
 *       一个流后面可以跟零个或多个中间操作, 其目的主要是打开流, 然后对流进行某种程度的映射/过滤, 交给下个操作(中间或终止操作)使用.
 *     这些操作都是惰性化的(lazy), 就是说, 仅仅调用这类方法并不会开始流的遍历.
 *
 *     中间操作包括:
 *          1.1 无状态操作(stateless operation)
 *              操作是无状态的，不需要知道集合中其他元素的状态，每个元素之间是相互独立的，比如map()、filter()等操作
 *          1.2 有状态操作(stateful operation)
 *              有状态操作，操作是需要知道集合中其他元素的状态才能进行的，比如sort()、distinct()。
 *  2. 终止操作(terminal operation)
 *       一个流只能有一个terminal操作, 当这个操作使用后, "流就被用光了", 无法再被操作. Terminal 操作的执行，才会真正开始
 *     流的遍历，并且会生成一个结果。 比如collect() foreach().
 *       终止操作包括:
 *          2.1 短路操作(short-circuiting): 不需要处理完所有元素即可结束整个过程.
 *          2.1 非短路操作(non-short-circuiting): 需要处理完所有元素才能结束整个过程.
 *
 *
 * ---------------
 * 生成流的方式:
 *      1. 从集合和数组生成:
 *          1.1 {@linkplain Collection#stream()}, 返回Stream<E>.
 *          1.2 {@linkplain Collection#parallelStream()} ()}, 返回Stream<E>.
 *          1.3 {@linkplain Arrays#stream(int[])}, 返回IntStream.
 *          1.4 {@linkplain Stream#of(Object[]), 内部调用Arrays的stream方法}, 和1.3的效果一样, 返回Stream<E>.
 *      2. 静态工厂:
 *          2.1 {@linkplain java.util.stream.DoubleStream#of(double...), 内部调用Arrays的stream方法},
 *          还有{@linkplain IntStream#range(int, int)}, {@linkplain java.util.stream.LongStream}. 目前只有这三种基本数据类型的Stream.
 * --------------
 *  Stream总结:
 *
 *      1. 不是数据结构，它没有内部存储，它只是用操作管道从 source（数据结构、数组、generator function、 IO channel）抓取数据.
 *      2. 它也绝不修改自己所封装的底层数据结构的数据。例如 Stream 的 filter 操作会产生一个不包含被过滤元 素的新 Stream，而不是从 source 删除那些元素.
 *      3. 所有 Stream 的操作必须以 lambda 表达式为参数.
 *      4. Intermediate操作永远是惰性化的, 仅在执行Terminal操作时才开始执行Intermediate操作.
 *      5. 当一个 Stream 是并行化的，就不需要再写多线程代码，所有对它的操作会自动并行进行的。
 *
 */
public class BasicUsage {
    public static void main(String[] args) {
        // 1. map: 1:1映射, 对于每一个输入元素, 按照转化规则转化为另一个元素.
        // 1.1 将字符串映射为数字.
        Stream<String> stream = Stream.of("1", "2", "3");
        List<Integer> integerList = stream.map(Integer::valueOf).collect(Collectors.toList());
        System.out.println("Map operation将字符串集合转化为数字集合:" + integerList);

        // 1.2 将字符串进行split, 然后再对每一个元素进行trim, 最后parseInt.
        integerList = Arrays.stream("1, 2, 3".split(","))
            .map(String::trim)
            .map(Integer::valueOf)
            .collect(Collectors.toList());
        System.out.println("CSV to list: " + integerList);

        // 1.1 flatMap: 1:n映射 TODO


        // 2. filter: 对Stream进行某项测试(DoublePredicate)，通过测试的元素被留下来生成一个新Stream.
        DoubleStream doubleStream = DoubleStream.of(1.3, 2, 3, 4);
        doubleStream.filter(ele -> ele >= 3).forEach(System.out::println);

        // 3. sort:
        // 3. forEach: 终止操作, Stream执行完此操作后, 不能再被"消费". 下面操作将会报错(接上2).
        try {
            doubleStream.filter(ele -> ele >= 3).forEach(System.out::println);
            // IllegalStateException: stream has already been operated upon or closed.
        } catch (IllegalStateException e) {
            System.out.println("Foreach之后再次使用流操作会报异常: " + e.getMessage());
        }

        // 4. 聚合操作.
        IntStream intStream = IntStream.rangeClosed(1, 10); // 数值流的构造(closed代表左闭右闭).
        // 求和
        System.out.println(intStream.reduce(1, Integer::sum));
        // 平均
        IntStream.rangeClosed(1, 10).average().ifPresent(System.out::println);

        // 5. 通过Collectors来进行reduce操作.
        // 5.1 groupBy/partitioningBy Stream 分组/分区操作.
        /**
         * 参考: {@linkplain Student#groupBy(Collection)}和{@linkplain Student#partitioningBy(Collection)} (Collection)}
         *
         * 其余的reduce操作见{@linkplain Student}
         */

    }

}
