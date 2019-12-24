package sort;

import java.util.Arrays;

/**
 * Harvest:
 *
 * 堆排序步骤: 1. 生成堆 2. 交换并重新生成堆.
 * ------------------------
 *
 * 堆排序相关概念:
 *   大顶堆: 一棵完全二叉树(每一层节点都要填满, 如果最后一层不是满的, 那么只能缺少右边的若干节点),
 * 且满足父节点大于所有子节点值的条件(左节点和右节点值的大小关系无所谓).
 *   完全二叉树可以用数组表示, 当父节点的index为i时, 那么左: 2*i + 1, 右: 2*i + 2.
 *
 *
 * ------------------------
 *
 * 算法应用:
 *   求解TOP K问题. 最大的TOP K用小顶堆, 最小的TOP K用大顶堆.
 *   1. 如何查询最热门的查询串?(最热门的 10 个查询串, TOP K)
 *      题目描述: 搜索引擎会通过日志文件把用户每次检索使用的所有查询串都记录下来，每个查询串的长度不超过 255 字节。
 *              假设目前有 1000w 个记录（这些查询串的重复度比较高，虽然总数是 1000w，但如果除去重复后，则不超过 300w 个）。
 *              请统计最热门的 10 个查询串，要求使用的内存不能超过 1G。（一个查询串的重复度越高，说明查询它的用户越多，也就越热门。）
 *
 *      > TIPS: 每个查询串最长为 255B，1000w 个串需要占用 约 2.55G 内存，因此，我们无法将所有字符串全部读入到内存中处理。
 *
 *      同2的解法.
 *
 *   2. 如何从大量数据中找出高频词?
 *      题目描述: 有一个1GB大小的文件, 文件里每一行是一个词, 每个词的大小不超过16B, 内存大小限制是1MB, 要求返回频数最高的100个词(TOP 100).
 *
 *      第一步: 先分治, 将大文件的每个词采取`hash(word) % 5000`, 将大文件分为5000个小文件, 每个小文件的大小为200kb, 如果有的文件大小
 *      超过1MB, 则采取同样的方式继续分解(换一个hash函数).
 *
 *      第二步: 使用HashMap来统计每个小文件中词的出现频数: map.put(word, 1) or map.put(word, map.get(word) + 1). O(N)
 *
 *      第三步: 构建一个大小为100的小顶堆, 来表示Top100的词. 具体做法: 依次遍历每个小文件, 如果词的数量小于100, 则直接插入到小顶堆.
 *      大于100则判断当前遍历词出现的次数是否大于堆顶词出现的次数, 小于则不予处理, 继续遍历. 大于则将堆顶的节点替换, 将堆重新调整为最小堆.
 *      遍历结束后, 小顶堆的词就是出现频数最高的100个词.  O(N*log100)
 *
 * ------------------------
 * {@linkplain java.util.PriorityQueue} Java里面的堆实现.
 */
public class HeapSort {

    public static void main(String[] args) {
        int[] nums = new int[]{0, 1, 2, 3, 4, 5};
//        int[] nums = new int[]{5, 4, 3, 2, 1, 0};


        System.out.println(Arrays.toString(new HeapSort().sort(nums)));
    }

    /**
     * 时间复杂度: O(N*logN), 工程中用的少是因为常数项较大-O(N).
     *  生成堆: O(log1) + O(log2) + O(log3) + ... + O(logn) = O(n)
     *  交换并重新生成堆: O(n*logn)
     *
     * 稳定性: 不稳定.
     * @param nums
     * @return
     */
    public int[] sort(int[] nums) {
        // 1. 构建大顶堆(大顶堆的概念见上).
        for (int i = 0; i < nums.length; i++) {
            heapInsert(nums, i);
        }
        System.out.println(Arrays.toString(nums));

        // 2. 每次将最大堆的跟节点与最后一个节点交换, 然后对剩余堆再构建一个最大堆, 当堆的大小为1的时候, 形成从小到大的排序.
        int heapSize = nums.length;
        while (heapSize > 0) {
            swap(nums, 0, --heapSize);
            heapify(nums, 0, heapSize);
        }

        return nums;
    }

    /**
     * 重新调整index上的节点, 使其满足大顶堆的性质(重新调整堆).
     *
     * @param nums
     * @param index
     * @param heapSize 堆的大小.
     */
    private void heapify(int[] nums, int index, int heapSize) {
        // 左节点
        int left;
        while ((left = (2 * index + 1)) < heapSize) { // 判断堆中是否还有节点.
            int right = left + 1;
            int largest = (right < heapSize && nums[left] < nums[right]) ? right : left;
            largest = nums[index] > nums[largest] ? index : largest;
            if (largest == index) {
                break;
            }

            // 如果最大节点不是根节点, 则将最大的孩子节点与它交换, 继续构建最大堆.
            swap(nums, index, largest);
            index = largest;
        }
    }

    /**
     * 将第i个节点插入到当前大顶堆.
     *
     * @param nums
     * @param i 要插入到大顶堆的节点的Index.
     */
    private void heapInsert(int[] nums, int i) {
        int parent;
        while ((parent = (i - 1) / 2) >= 0) { // 判断是否还有父节点.
            // 如果父节点小于子节点, 则交换; 否则代表当前已是大顶堆, 直接break.
            if (nums[parent] < nums[i]) {
                swap(nums, parent, i);
                i = parent;
            } else {
                break;
            }
        }
    }


    private void swap(int[] nums, int i, int j) {
        int temp = nums[i] ^ nums[j];
        nums[i] = temp ^ nums[i];
        nums[j] = nums[i] ^ temp;
    }
}
