package 海量数据;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 从设备角度, 负载均衡分为:
 * 1. 软件负载均衡: LVS Nginx Tengine, 优点是成本比较低, 缺点是需要专业的团队去维护, 自己去采坑和DIY.
 * 2. 硬件负载均衡: F5 Array, 属于商业的负载均衡器, 性能较好, 但是费用比较昂贵.
 *
 * 从技术来讲, 负载均衡分为:
 * 1. 服务器端负载均衡. 当访问服务的时候, 请求会先到另一台服务器, 然后这台服务器会根据算法把请求分发到对应服务器.
 * 2. 客户端负载均衡. 在一台服务器是那个维护着调用方的所有机器IP 名称等信息, 当访问一个服务的时候, 直接在客户端用负载均衡算法, 选择一台服务器去请求.
 *
 *
 * 从算法来讲, 分为:
 * 1. 随机
 * 1.1 完全随机
 * 1.2 加权随机
 *
 * 2. 轮询
 * 2.1 完全轮询  {@linkplain #go(int)}
 * 2.2 加权轮询 {@linkplain #goWithWight(int, int)}}
 * 2.3 平滑加权轮询 TODO
 *
 * 3. 哈希: 包括普通哈希（根据客户端的IP地址将请求发送到固定的机器）、一致性哈希算法、带有虚拟节点的一致性哈希算法。除此之外，还可用于分布式下数据分布场景
 * 比如Session落在哪个机器的问题。
 * 3.1 带有虚拟节点的一致性哈希算法 {@linkplain Hash}
 *
 * 4. 最小活跃次数/最小压力
 * 选择一台最‘悠闲’的服务器，如果A服务器100请求, B服务器有5个请求, 而C只有3个请求, 那么毫无以为选择C, 这种负载均衡算法是比较科学的.
 *
 *
 *   在实际的负载均衡下, 可能会将多个负载均衡算法合在一起实现, 比如先根据最小压力算法, 党有几台服务器一样小的时候, 再根据权重取出一台服务器,
 * 如果权重也一样, 再随机取一台.
 */
public class LoadBalance {

    public static List<String> SERVER_LIST = Arrays.asList("192.168.1.1", "192.168.1.2", "192.168.1.3", "192.168.1.4");

    public static LinkedHashMap<Integer, String> SERVER_LIST_WITH_WIGHT = new LinkedHashMap<>();

    static {
        // 总权重为10. 对10取余后, 各区间分布值如下：
        SERVER_LIST_WITH_WIGHT.put(1, "WIGHT_" + SERVER_LIST.get(0)); // 0
        SERVER_LIST_WITH_WIGHT.put(3, "WIGHT_" + SERVER_LIST.get(1)); // 1 2 3, 1 2 3取余减1后, 仍 < 3.
        SERVER_LIST_WITH_WIGHT.put(4, "WIGHT_" + SERVER_LIST.get(2)); // 4 5 6 7, 4 5 6 7取余减(1+3)后, 仍小于5.
        SERVER_LIST_WITH_WIGHT.put(2, "WIGHT_" + SERVER_LIST.get(3)); // 8 9
    }

    public static void main(String[] args) {
        // 每个线程都跑1000此请求，最终500个请求1, 500个请求2...
        // new Thread(() -> go(1000)).start();
        // new Thread(() -> go(1000)).start();


        System.out.println("Wight range start...");
        int allWight = SERVER_LIST_WITH_WIGHT.keySet().stream().mapToInt(a -> a).sum();
        goWithWight(1000, allWight);
    }

    // atomic 保证index的线程安全.
    private static AtomicInteger index = new AtomicInteger();

    /**
     * 加权轮询
     *
     * @param requestCount
     */
    public static void goWithWight(int requestCount, int allWight) {
        for (int i = 0; i < requestCount; i++) {
            int range = i % allWight;
            for (Map.Entry<Integer, String> entry : SERVER_LIST_WITH_WIGHT.entrySet()) {
                Integer item = entry.getKey();
                if (range < item) {
                    System.out.println(entry.getValue());
                    break;
                }
                range = range - item;
            }
        }
    }

    /**
     * 普通轮询
     * @param requestCount
     */
    public static void go(int requestCount) {
        for (int i = 0; i < requestCount; i++) {
            System.out.println(SERVER_LIST.get(index.getAndAdd(1)));
            if (index.get() == SERVER_LIST.size()) {
                index.set(0);
            }
        }
    }
}


class Hash {
    /**
     * 带有虚拟节点的一致性Hash算法。
     *
     * @param client
     * @return
     */
    private static String go(String client) {
        int nodeCount = 20;
        TreeMap<Integer, String> treeMap = new TreeMap<>();

        for (String s : LoadBalance.SERVER_LIST) {
            for (int i = 0; i < nodeCount; i++)
                treeMap.put((s + "--服务器---" + i).hashCode(), s);
        }

        int clientHash = client.hashCode();
        SortedMap<Integer, String> subMap = treeMap.tailMap(clientHash); // 根据clientHash找环上的第一个节点，可能是虚拟的。
        Integer firstHash;
        if (subMap.size() > 0) {
            firstHash = subMap.firstKey();
        } else {
            firstHash = treeMap.firstKey();
        }
        return treeMap.get(firstHash);
    }

    public static void main(String[] args) {
        System.out.println(go("今天天气不错啊"));
        System.out.println(go("今天天气不错啊"));
        System.out.println(go("192.168.5.258"));
        System.out.println(go("0"));
        System.out.println(go("-110000"));
        System.out.println(go("风雨交加"));
    }

}