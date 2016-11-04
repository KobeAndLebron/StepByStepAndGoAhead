package lettcode.easy;

import com.gohead.shared.test.ParentTest;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.runners.Parameterized.Parameters;

/**
 * Created by chenjingshuai on 16-11-4.
 */
public class MoveZerosTest extends ParentTest<Object> {
    private int[] inputNums;
    private static final MoveZeros MOVE_ZEROS= new MoveZeros();

    public MoveZerosTest(int[] nums, int[] expectedObj) {
        super(expectedObj);
        inputNums = nums;
    }

    @Parameters
    public static Collection<Object[]> generateParameters() {
        return Arrays.asList(new Object[][]{
                new Object[]{new int[]{0, 1, 2, 3, 4}, new int[]{1, 2, 3, 4, 0}},
                new Object[]{new int[]{0}, new int[]{0}},
                new Object[]{new int[]{0, 0, 0, 0}, new int[]{0, 0, 0, 0}},
                new Object[]{new int[]{1, 2, 0, 0, 0, 3, 4}, new int[]{1, 2, 3, 4, 0, 0, 0}},
                new Object[]{new int[]{1, 2, 0, 3, 0, 0, 4, 4, 0, 0, 0, 5}, new int[]{1, 2, 3, 4, 4, 5, 0, 0, 0, 0, 0, 0}}
        });
    }

    public void test() {
        MOVE_ZEROS.moveZeroes(inputNums);
        generatedObj = inputNums;
    }

    @Override
    protected void specifyCase() {
        specifyCase(-1);
    }

}
