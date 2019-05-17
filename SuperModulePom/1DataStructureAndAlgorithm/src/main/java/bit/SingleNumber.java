package bit;

import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author ChenJingShuai
 * @date 19-5-16
 */
public class SingleNumber {

    /**
     *  https://leetcode.com/problems/single-number-iii/

         Given an array of numbers nums, in which exactly two elements appear only once and all
     the other elements appear exactly twice. Find the two elements that appear only once.

        Example:
            Input:  [1,2,1,3,2,5]
            Output: [3,5]

        Note:
            1. The order of the result is not important. So in the above example, [5, 3] is
       also correct.
            2. Your algorithm should run in linear runtime complexity. Could you implement it
        using only constant space complexity?

        // Harvest
            (A & -A)将保留A从右起第一个不为0的位置,然后将其余位均置为0.
            e.g. 100000 & 100000 = 1000000
                 111111 & 000001 = 0000001
                 111101 & 000011 = 0000001

     * @param nums
     * @return
     */
    public int[] singleNumber3(int[] nums) {
        // 1. 将数组所有元素进行XOR操作, 得到diff1 ^ diff2.
        int xorResult = 0;
        for (int num : nums) {
            xorResult ^= num;
        }
        // 2. 找到结果1中的一个值为1的bit, 表示这两个数在这个位置的bit不同.
        // 等价于 s= xorResult & -xorResult
        int s = 1;
        for (int i = 0; i < 30; i++) {
            if ((xorResult & s) > 0){
                break;
            }
            s = s << 1;
        }
        // 3. 通过2中的位置, 将所有数分为两组, 组1: x1 x1 x2 x2... diff1, 组2: x3 x4... diff2.
        // 最后分别得到diff1和diff2
        int diff1 = 0;
        for (int num : nums) {
            if ((num & s) > 0) {
                diff1 ^= num;
            }
        }

        int[] diffArr = new int[2];
        diffArr[0] = diff1;
        diffArr[1] = diff1 ^ xorResult;

        return diffArr;
    }

    @Test
    public void singleNumber3() {
        int[] nums = new int[] {1, 2, 3, 3, 2, 1, 4, 5};

        int[] diffArr = singleNumber3(nums);
        System.out.println(Arrays.toString(diffArr));
        Assert.assertEquals(diffArr[0] ^ diffArr[1] ^ 4, 5);
        Assert.assertEquals(diffArr[0] ^ diffArr[1] ^ 5, 4);
        System.out.println(Integer.MAX_VALUE == ((1 << 31) - 1));

        int s = 1;
        for (int i = 0; i < 30; i++) {
            s <<= 1;
        }
        System.out.println(s == 1 << 30);
    }
}
