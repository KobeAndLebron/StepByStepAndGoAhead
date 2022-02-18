package 海量数据;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

/**
 * 布隆过滤器.
 *
 * -------------
 *
 * 用途: 1. 海量数据查重(除了这种方法, 还有BitMap位图法).
 *          比如: 垃圾邮件过滤. 爬虫过滤, 已经抓到的URL就不再抓, 可用Bloom filter过滤.
 *      2. 避免缓存穿透(访问缓存和数据库中都没有的数据, 而用户不断发起类似这样的请求, 这些请求直接会发往数据库, 从而导致数据库压力过大, 严重时被击垮)
 *          缓存穿透的解决方案:
 *          a. 在接口层增加校验, 不合法的参数直接Return, 比如对ID做基础校验, ID小于0的请求直接拦截.
 *          b. 从缓存中取不到数据, 在DB也没有查到, 这时也可以将对应的Key的Value写为null 稍后重试等这样的值[不推荐].
 *          c. 缓存里面加上布隆过滤器, 先把我们数据库的数据都加载到我们的布隆过滤器中[新增数据后去更新布隆过滤器], 就可以判断ID是否存在, 如果
 *          不存在就不去查DB了, 直接Return一个空数据就行.
 *
 * -------------
 * 基本原理:
 * 新增时, 对于每一个元素来说:
 *  1. 通过k个(无偏)hash函数计算得到k个hash值
 *  2. 对这k个hash值依次取模数组长度，得到数组索引
 *  3. 将计算得到的数组索引下标位置数据修改为1
 * 新增完成后, bitMap上有n个位置被设置为了1.
 * 查询时, 对于每一个元素来说:
 *   1. 通过k个hash函数对这个元素计算得到k个hash值.
 *   2. 对这k个hash值依次取模数组长度, 得到数组索引.
 *   3. 判断索引处的值是否全部为1，如果全部为1则存在（这种存在可能是误判），如果存在一个0则必定不存在.
 *
 * ------------
 *
 * 优点:
 *  相比于HashMap或bitMap，优点在于时间和空间[1/4或1/8 HashMap的空间]效率远远超过一般的算法.
 *
 *  例:
 *    BloomFilter存一百万个int类型数字，需要的位数为7298440，700多万位。
 *  理论上存一百万个数，一个int是4字节32位，需要481000000=3200万位。如果使用HashMap去存，按HashMap50%的存储效率，需要6400万位。
 *  可以看出BloomFilter的存储空间很小，只有HashMap的1/10左右
 *
 * 缺点:
 *  1. 存在一定的误判率[误判率越低, 需要的Hash函数越多, 时间和空间越大].
 *  2. 删除元素时较困难.
 *
 */
public class TestBloomFilter {

    // 数据量为100万
    private static int total = 1000000;
//    private static BloomFilter<Integer> bf = BloomFilter.create(Funnels.integerFunnel(), total);
    private static BloomFilter<Integer> bf = BloomFilter.create(Funnels.integerFunnel(), total, 0.001);

    public static void main(String[] args) {
        // 初始化1000000条数据到过滤器中
        for (int i = 0; i < total; i++) {
            bf.put(i);
        }

        // 匹配已在过滤器中的值，是否有匹配不上的
        for (int i = 0; i < total; i++) {
            // 返回false表明一定不在集合中.
            if (!bf.mightContain(i)) {
                System.out.println("有坏人逃脱了~~~");
            }
        }

        // 匹配不在过滤器中的10000个值，有多少匹配出来
        int count = 0;
        for (int i = total; i < total + 10000; i++) {
            if (bf.mightContain(i)) {
                // 返回true表明元素可能在集合中. 即对于不存在的元素, 只有0.001的误判率误判它存在.
                count++;
            }
        }
        System.out.println("误伤的数量：" + count);
    }

}