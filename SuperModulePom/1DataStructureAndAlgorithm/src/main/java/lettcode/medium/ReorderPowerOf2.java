package lettcode.medium;

import java.util.Arrays;

/**
 * https://leetcode.com/problems/reordered-power-of-2/
 *
     Starting with a positive integer N, we reorder the digits in any order (including the original order) such that
   the leading digit is not zero.

     Return true if and only if we can do this in a way such that the resulting number is a power of 2.

     Example 1:
     Input: 1
     Output: true

     Example 2:
     Input: 10
     Output: false

     Example 3:
     Input: 16
     Output: true

     Example 4:
     Input: 24
     Output: false

     Example 5:
     Input: 46
     Output: true

     Note:

     1 <= N <= 10^9

     两种解法都是计算数字N上0-9每一个digit的个数.
 *
 * @author chenjingshuai
 * @date 19-3-26
 */
public class ReorderPowerOf2 {

    /**
     * Resolution 1.
     *
     * @param N
     * @return
     */
    public boolean reorderedPowerOf2(int N) {
        int[] numberCount = getNumberCount(N);

        int i = 0;
        // 最多左边移31位.
        while (i < 32) {
            if (Arrays.equals(numberCount, getNumberCount(1 << i))) {
                return true;
            }
            i++;
        }
        return false;
    }

    /**
     * 获取数字N0-9每个数的的个数.
     *
     * @param N
     * @return
     */
    private int[] getNumberCount(int N) {
        // 下标0-9依次代表数字0-9的个数.
        int[] numberCount = new int[10];
        for (int i = N; i > 0; ) {
            int base = i / 10;
            int temp = i - base * 10;
            numberCount[temp]++;

            i = base;
        }
        return numberCount;
    }

    /**
     * Resolution 2.
     */
    public boolean reorderedPowerOf2_1(int N) {
        long c = counter(N);
        for (int i = 0; i < 32; i++) {
            if (counter(1 << i) == c) {
                return true;
            }
        }
        return false;
    }

    private long counter(int N) {
        long res = 0;
        for (; N > 0; N /= 10) {
            res += Math.pow(10, N % 10);
        }
        return res;
    }
}
