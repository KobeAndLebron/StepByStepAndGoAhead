package lettcode.medium;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/product-of-array-except-self/submissions/
 * 除自身以外的乘积.
 *
 * 关键点自在于第二遍遍历要用到第一遍遍历得到的结果, 即左右夹攻.
 */
public class ProductOfArrayExceptSelf {
    public int[] productExceptSelf(int[] nums) {
        int[] outputNums = new int[nums.length];

        int k = 1;
        // 从左往右乘, 分别得到前x, 1, 2, 3, ... n-1的乘积.
        for (int i = 0; i < nums.length; i++) {
            outputNums[i] = k;
            k = k * nums[i];
        }

        k = 1;
        // nums存的是1, 2, 3, 4... n的数值.
        for (int j = outputNums.length - 1; j >= 0; j--) {
            outputNums[j] = outputNums[j] * k;
            k = k * nums[j];
        }

        return outputNums;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new ProductOfArrayExceptSelf().productExceptSelf(new int[]{1, 2, 3, 4})));
    }
}
