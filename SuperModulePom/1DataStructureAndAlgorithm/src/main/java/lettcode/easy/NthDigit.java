package lettcode.easy;

/**
 *
 https://leetcode.com/problems/nth-digit/:
 Find the nth digit of the infinite integer sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ...

 Note:
 n is positive and will fit within the range of a 32-bit signed integer (n < 231).

     Example 1:

     Input:
     3

     Output:
     3
     Example 2:

     Input:
     11

     Output:
     0

     Explanation:
     The 11th digit of the sequence 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, ... is a 0, which is part of the number 10.
 *
 * @author chenjingshuai
 * @date 19-4-16
 */
public class NthDigit {
    public int findNthDigit(int n) {
        // 当前位数
        int base;
        // 当前位数的最大位数
        int max = 0;

        // 下一个位数
        int nextBase = 1;
        // 下一位数的最大位数
        int nextMax = 9;

        while ((n > nextMax) || (n < max)) {
            base = nextBase;
            max = nextMax;

            nextBase += 1;
            nextMax = (int) (nextMax + (nextBase * (Math.pow(10, nextBase) - Math.pow(10, base))));
        }

        return findNthDigit(n - max, nextBase);
    }


    public int findNthDigit1(int n) {
        // 当前位数
        int base = 1;
        // 当前位数的最大位数
        int max = 9;

        // 当n大于当前位数的最大位数时, 结束循环.
        while (n > max) {
            int curDigits = (int) (base * (Math.pow(10, base) - Math.pow(10, base - 1)));
            n -= curDigits;

            base += 1;
            max = (int) (max + (base * (Math.pow(10, base) - Math.pow(10, base - 1))));
        }

        return findNthDigit(n, base);
    }

    /**
     *
     * @param n 当前区间的第n位数.
     * @param base 当前区间的base.
     * @return
     */
    private int findNthDigit(int n, int base) {
        // 第n位数在当前区间的位置.
        int nPosition = n / base;
        // nPosition位置对应的数字值.
        int nPositionNumber = ((int) Math.pow(10, base - 1)) + nPosition - 1;

        nPosition = n % base;

        // n为当前值的最后一个digit.
        if (nPosition == 0) {
            char[] numberArr = Integer.toString(nPositionNumber).toCharArray();
            return Integer.valueOf(numberArr[numberArr.length - 1] + "");
        } else { // n位于下一个值.
            char[] numberArr = Integer.toString(nPositionNumber + 1).toCharArray();
            return Integer.valueOf(numberArr[nPosition - 1] + "");
        }
    }
}

