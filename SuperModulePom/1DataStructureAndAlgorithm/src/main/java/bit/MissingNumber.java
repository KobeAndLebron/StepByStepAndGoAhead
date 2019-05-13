package bit;

import org.junit.Assert;
import org.junit.Test;

/**
 * 方法1比方法2运行的快, 因为基数比较小.
 *
 *  Harvest
 *  逻辑或(^)的运算:
 *     p XOR p = 0
 *     P XOR 0 = p
 *     P XOR (Q XOR R) = (P XOR Q) XOR R
 *     P XOR Q XOR Q = P XOR 0 = P
 *  使用异或来交换两个数:
 *      x = x ^ y
 *      y = x ^ y (y = x ^ y ^ y = x ^ 0 = x)
        x = x ^ y (y = x ^ y ^ x = x ^ x ^ y = y)
 *
 *
 * @author chenjingshuai
 * @date 19-5-13
 */
public class MissingNumber {

    /**
     * 方法1: 循环2次, 时间复杂度为2N -> O(N),
     *
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums) {
        int diff = 0;
        for (int num : nums) {
            diff = diff ^ num;
        }

        for (int i = 0; i <= nums.length; i++) {
            diff = diff ^ i;
        }

        return diff;

    }

    /**
     * 方法2: 循环一次, 时间复杂度为N -> O(N),
     *
     * @param nums
     * @return
     */
    public int missingNumber1(int[] nums) {
        int diff = nums.length;
        for (int i = 0; i < nums.length; i++) {
            diff = diff ^ nums[i] ^ i;
        }

        return diff;

    }

    @Test
    public void test() {
        int[] nums = new int[]{3, 0, 1};
        Assert.assertEquals(missingNumber(nums), 2);
        Assert.assertEquals(missingNumber1(nums), 2);

        nums = new int[]{3, 0, 1, 2, 6, 7, 5, 4, 9};
        Assert.assertEquals(missingNumber(nums), 8);
        Assert.assertEquals(missingNumber1(nums), 8);

        nums = new int[]{};
        Assert.assertEquals(missingNumber(nums), 0);
        Assert.assertEquals(missingNumber1(nums), 0);
    }

}
