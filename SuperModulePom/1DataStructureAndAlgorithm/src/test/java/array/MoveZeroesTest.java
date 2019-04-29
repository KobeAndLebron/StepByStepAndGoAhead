package array;

import com.gohead.shared.test.ParentTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runners.Parameterized;

import java.util.Arrays;

/**
 * Created by chenjingshuai on 19-4-29.
 */
public class MoveZeroesTest extends ParentTest<int[]> {
    private int[] input;

    public MoveZeroesTest(int caseId, int[] expectedObj, int[] input) {
        super(caseId, expectedObj);
        this.input = input;
    }

    @Parameterized.Parameters
    public static Object[][] generateParameters() {
        return new Object[][]{
            new Object[]{1, new int[]{1, 3, 12, 0, 0}, new int[]{0, 1, 0, 3, 12}},
            new Object[]{2, new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}},
            new Object[]{3, new int[]{1, 1, 1, 0}, new int[]{0, 1, 1, 1}}
        };
    }

    @Test
    public void test() {
        int[] inputCopy = Arrays.copyOf(input, input.length);
        new MoveZeroes().moveZeroes(input);
        new MoveZeroes().moveZeroes1(inputCopy);
        this.generatedObj = input;
        Assert.assertArrayEquals(inputCopy, input);

        System.out.println(Arrays.toString(input));
    }
}
