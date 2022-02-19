package lettcode.medium;

/**
 * 峰值相关题目. 共两个题目.
 */
public class FindInMountainArray {

    /**
     * 峰值元素是指其值严格大于左右相邻值的元素。
     *
     * 给你一个整数数组 nums，找到峰值元素并返回其索引。数组可能包含多个峰值，在这种情况下，返回 任何一个峰值 所在位置即可。
     *
     * 你可以假设 nums[-1] = nums[n] = -∞ 。
     *
     * 你必须实现时间复杂度为 O(log n) 的算法来解决此问题。
     *
     *
     * 示例 1：
     *
     * 输入：nums = [1,2,3,1]
     * 输出：2
     * 解释：3 是峰值元素，你的函数应该返回其索引 2。
     * 示例 2：
     *
     * 输入：nums = [1,2,1,3,5,6,4]
     * 输出：1 或 5
     * 解释：你的函数可以返回索引 1，其峰值元素为 2；
     *      或者返回索引 5， 其峰值元素为 6。
     *
     * @param nums
     * @return
     */
    public int findPeakElement(int[] nums) {
        int n = nums.length;
        int left = 0, right = n - 1;

        while (left < right) {
            int mid = left + right + 1 >> 1;
            if (nums[mid] > nums[mid - 1]) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }

    /**
     * 给你一个 山脉数组 mountainArr，请你返回能够使得 mountainArr.get(index) 等于 target 最小 的下标 index 值。
     *
     * 如果不存在这样的下标 index，就请返回 -1。
     *
     *  
     *
     * 何为山脉数组？如果数组 A 是一个山脉数组的话，那它满足如下条件：
     *
     * 首先，A.length >= 3
     *
     * 其次，在 0 < i < A.length - 1 条件下，存在 i 使得：
     *
     * A[0] < A[1] < ... A[i-1] < A[i]
     * A[i] > A[i+1] > ... > A[A.length - 1]
     *  
     *
     * 你将 不能直接访问该山脉数组，必须通过 MountainArray 接口来获取数据：
     *
     * MountainArray.get(k) - 会返回数组中索引为k 的元素（下标从 0 开始）
     * MountainArray.length() - 会返回该数组的长度
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/find-in-mountain-array
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     *
     * https://leetcode-cn.com/problems/find-in-mountain-array/
     *
     * @param target
     * @param mountainArr
     * @return
     */
    public int findInMountainArray(int target, MountainArray mountainArr) {
        int n = mountainArr.length();
        int left = 1, right = n - 2;

        // 先找出峰值(最大值)所在的位置.
        while (left < right) {
            int mid = left + right + 1 >> 1;
            if (mountainArr.get(mid) > mountainArr.get(mid - 1)) {
                left = mid;
            } else {
                right = mid - 1;
            }
        }

        // 二分搜索左半部分的值.[递增]
        int i = binarySearchInLeft(target, 0, left, mountainArr);
        if (i >= 0) {
            return i;
        } else {
            // 二分搜索右半部分的值.[递减]
            return binarySearchInRight(target, left, mountainArr.length() - 1, mountainArr);
        }

    }

    private int binarySearchInLeft(int target, int start, int end, MountainArray mountainArr) {
        while (start <= end) {
            int mid = start + end + 1 >> 1;
            if (mountainArr.get(mid) > target) {
                end = mid - 1;
            } else if (mountainArr.get(mid) < target) {
                start = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

    private int binarySearchInRight(int target, int start, int end, MountainArray mountainArr) {
        while (start <= end) {
            int mid = start + end + 1 >> 1;
            if (mountainArr.get(mid) < target) {
                end = mid - 1;
            } else if (mountainArr.get(mid) > target) {
                start = mid + 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

    public static class MountainArray {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 10, 8, 7, -1, -2, -3, -5};

        public int get(int k) {
            return arr[k];
        }

        public int length() {
            return arr.length;
        }
    }

    public static void main(String[] args) {
        System.out.println(new FindInMountainArray().findInMountainArray(-1, new MountainArray()));

        System.out.println(new FindInMountainArray().findInMountainArray(0, new MountainArray()));


        System.out.println(new MountainArray().arr[new FindInMountainArray().findPeakElement(new MountainArray().arr)]);

    }
}
