package lettcode.easy;

import org.junit.Assert;
import org.junit.Test;

/**
 *  https://leetcode.com/problems/jewels-and-stones
 *
 *
   You're given strings J representing the types of stones that are jewels, and S representing the
 stones you have.  Each character in S is a type of stone you have.  You want to know how many of
 the stones you have are also jewels.

   The letters in J are guaranteed distinct, and all characters in J and S are letters. Letters
 are case sensitive, so "a" is considered a different type of stone from "A".

    Example 1:
        Input: J = "aA", S = "aAAbbbb"
        Output: 3

    Example 2:
        Input: J = "z", S = "ZZ"
        Output: 0

     Note:
        S and J will consist of letters and have length at most 50.
     The characters in J are distinct.
 * @author ChenJingShuai
 * @date 19-5-15
 */
public class JewelsAndStones {

    /**
     * Harvest
     *   解法1: 小写字母a的ASCII码为65, 小写字母z的ASCII码为90.
     *       大写字母A的ASCII码为97 , 大写字母Z的ASCII码为122.
     *       即a-Z最多有58个字符.
     *       
     * @param jewels
     * @param stones
     * @return
     */
    public int numJewelsInStones(String jewels, String stones) {
        int[] stoneNum = new int[58];
        for (char stone : stones.toCharArray()) {
            stoneNum[stone - 65]++;
        }

        int sum = 0;
        for (char jewel : jewels.toCharArray()) {
            sum += stoneNum[jewel - 65];
        }

        return sum;
    }

    /**
     * 解法2: 将stones的非"jewels"字符替换掉, 剩下的则为"jewels"类型的stone.
     * @param jewels
     * @param stones
     * @return
     */
    public int numJewelsInStones1(String jewels, String stones) {
        return stones.replaceAll("[^" + jewels + "]", "").length();
    }

    @Test
    public void test() {
        String jewels = "Ab";
        String stones = "aABbcccZ";
        Assert.assertEquals(2, numJewelsInStones1(jewels, stones));
        Assert.assertEquals(2, numJewelsInStones(jewels, stones));

    }
}
