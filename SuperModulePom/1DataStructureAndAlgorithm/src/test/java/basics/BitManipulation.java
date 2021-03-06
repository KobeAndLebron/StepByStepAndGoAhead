package basics;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * Created by chenjingshuai on 17-1-11.
 */
public class BitManipulation {

    @Test
    public void test() {
        /**************************移位运算*****************************/
        // 0000 0000 .... 0010
        Assert.assertEquals(1 << 1, 2);

        // 1000 0000 0000 ....
        Assert.assertEquals(1 << 31, Integer.MIN_VALUE);

        // 0000 0000 .... 0001
        Assert.assertEquals(1 << 32, 1);

        // 0111 1111 1111 ....
        Assert.assertEquals((1 << 31) - 1, Integer.MAX_VALUE);

        /**************************加减*****************************/

        // 0100 0000 0000 ....　
        // 0100 0000 0000 ....
        // 1000 0000 0000 .... 补码
        Assert.assertEquals(((1 << 30) + (1 << 30)), Integer.MIN_VALUE);

        // 0111 1111 1111 .... 1111 max
        // 0000 0000 0000 .... 0010 2
        // 1000 0000 0000 .... 0001 结果为补码, 负数的补码不是本身, 需要求其原码, 才是其真值.
        // 1000 0000 0000 .... 0000 反码(符号位参与反码<=>补码的运算过程)
        // 1111 1111 1111 .... 1111 原码(符号位不参与原码<=>反码的运算过程, 只有这步符号位不参加运算!!!).

        // 1000 0000 0000 .... 0000 min 原码 = 补码
        // 0000 0000 0000 .... 0001 1
        // 1000 0000 0000 .... 0001 补码
        // 1000 0000 0000 .... 0000 反码
        // 1111 1111 1111 .... 1111 原码
        Assert.assertEquals(Integer.MIN_VALUE + 1, Integer.MAX_VALUE + 2);

        Assert.assertEquals(Integer.MIN_VALUE, Integer.MAX_VALUE + 1);


        // (-1) + (-3)
        // 原码　　反码    补码        补码      反码    原码
        // 1001   1110    1111 \
        //                       =   1100     1011    1100 = - 2^2 = -4
        // 1011   1100    1101 /
    }
}
