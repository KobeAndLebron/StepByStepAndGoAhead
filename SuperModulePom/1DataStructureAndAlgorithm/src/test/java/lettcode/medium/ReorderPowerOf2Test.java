package lettcode.medium;

import com.gohead.shared.test.ParentTest;
import org.junit.Assert;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by chenjingshuai on 19-4-22.
 */
public class ReorderPowerOf2Test extends ParentTest<Boolean> {
    private int N;

    public ReorderPowerOf2Test(int caseId, Boolean expectedObj, int N) {
        super(caseId, expectedObj);
        this.N = N;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> generateParameters(){
        return Arrays.asList(new Object[][]{
                new Object[]{1, false, 10},
                new Object[]{2, true, 1},
                new Object[]{3, false, 24},
                new Object[]{4, true, 46},
                // Harvest: Math.double 返回double, 不能直接赋值给int类型, 否则运行时会报错.
                new Object[]{5, false, (int) Math.pow(10, 9)},
                new Object[]{6, false, (int) Math.pow(10, 9) - 1},
            }
        );
    }

    @Override
    protected void test() {
        System.out.println(N);
        generatedObj = new ReorderPowerOf2().reorderedPowerOf2(N);
        Boolean generateObj1 = new ReorderPowerOf2().reorderedPowerOf2_1(N);
        Assert.assertEquals(generateObj1, generatedObj);
    }
}
