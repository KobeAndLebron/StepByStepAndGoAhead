package lettcode.easy;

import com.gohead.shared.test.ParentTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * gains {@linkplain basics.BitManipulation}
 * Created by chenjingshuai on 17-1-11.
 */
public class Number1BitsTest extends ParentTest<Integer> {
    private final int unsignedInteger;

    public Number1BitsTest(Integer expectedOb, int unsignedInteger) {
        super(expectedOb);
        this.unsignedInteger = unsignedInteger;
    }

    @Parameters
    public static Collection<Object[]> generateParameters() {
        return Arrays.asList(new Object[][]{
                new Object[]{3, 11},
                new Object[]{0, 0},
                new Object[]{1, 16},
                new Object[]{31, Integer.MAX_VALUE},
                /*
                 gains
                 　　　　1. 加减法使用补码进行运算.
                           如果操作数为正数, 则直接进行二进制位操作(正数的补码是本身);
                           如果操作数为负数, 则使用补码进行运算; 两者都进行符号位的运算.
　                       如果结果的符号位是１, 则结果为补码, 需要求出它的原码, 才是最后的结果; 如果结果的符号位是0, 由于正数的补码和原码相同.
                       2. MAX: 0111 1111, 1: 0000 0001, 相加得: 1000 0000, 代表负数的最大值.
                */
                new Object[]{1, Integer.MAX_VALUE + 1},
        });
    }

    @Override
    protected void test() {
        generatedObj = new Number1Bits().hammingWeight(unsignedInteger);
        judgeBySys();
        generatedObj = new Number1Bits().hammingWeight1(unsignedInteger);
        judgeBySys();
        generatedObj = new Number1Bits().hammingWeight2(unsignedInteger);
    }

    @Override
    protected void setSelfJudged() {
        specifyCase(-1);
    }
}