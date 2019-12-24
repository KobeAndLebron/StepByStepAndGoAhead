package sort;

/**
 * Harvest
 *
 * Master 公式来计算递归函数的复杂度,
 *  假设一个递归函数的时间复杂度为: T(n) = a*T(n/b) + O(n^d).
 *      T(n)表示整个递归函数的时间, n表示样本量.
 *      a表示调用子过程的次数[递归发生了多少次], n/b 表示子问题样本量的规模[递归函数的样本量],
 *      O(n^d) 表示除了递归行为之外的复杂度.
 *  当d 等于 log b(a), 则时间复杂度为O( (N ^ log b(a)) * logN).  [log b(a)表示log以b为底的a的对数.
 *  当d 大于 log b(a), 则时间复杂度为O(N^d).
 *  当d 小于 log b(a), 则时间复杂度为O(N^log b(a)).
 *
 * --------------------
 *  归并排序的时间复杂度T(n) = T(n/2) + T(n/2) + O(n) = 2*T(n/2) + O(n), 由于log 2(2) == 1,
 * 所以归并排序的时间复杂度为O(N ^ (log 2(2)) * logN) = O(N*longN).
 *
 * --------------------
 * 递归: 保证递归顺利执行的是函数栈, 当调用子过程的时候, 会将自己先压入栈, 栈中记录自己的局部变量值 行号等信息.
 */
public class MergeSort {

    /**
     * ascending.
     *
     * 平均/最坏/最好时间复杂度: O(N*logN).
     * 空间复杂度: O(n)
     * 稳定性: 稳定.
     *
     * @param nums
     * @return
     */
    public int[] mergeSort(int[] nums) {
        if (nums == null || nums.length < 2) {
            return nums;
        }

        sortProcess(nums, 0, nums.length - 1);

        return nums;
    }

    private void sortProcess(int[] nums, int left, int right) {
        if (left <= right) {
            return;
        }
        // Harvest 这样会溢出得到负数, 正确姿势: mid = left + ((right - left) >> 1).
        int mid = (left + right) / 2;
        sortProcess(nums, left, mid);
        sortProcess(nums, mid + 1, right);
        merge(nums, left, mid, right);
    }

    private void merge(int[] nums, int left, int mid, int right) {
        int[] tmp = new int[right - left + 1];

        int i = 0;
        int iLeft = left;
        int iRight = mid + 1;
        while (iLeft <= mid && iRight <= right) {
            tmp[i++] = nums[iLeft] < nums[iRight] ? nums[iLeft++] : nums[iRight++];
        }

        // 肯定有一个有剩余, 即下面的where循环只会走一个.
        while (iLeft <= mid) {
            tmp[i++] = nums[iLeft++];
        }

        while (iRight <= right) {
            tmp[i++] = nums[iRight++];
        }

        // System.arraycopy(tmp, 0, nums, left + 0, tmp.length)
        for (int j = 0; j < tmp.length; j++) {
            nums[left + j] = tmp[j];
        }
    }

}
