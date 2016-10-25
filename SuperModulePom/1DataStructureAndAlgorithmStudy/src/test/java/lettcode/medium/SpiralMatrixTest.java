package lettcode.medium;

import com.gohead.shared.test.ParentTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.runners.Parameterized.Parameters;

/**
 * Created by ChenJingShuai on 2016/10/17.
 */
public class SpiralMatrixTest extends ParentTest<List<Integer>> {
    private int[][] matrix;

    @Test
    public void commonTest() {
        this.generatedObj = new SpiralMatrix().spiralOrder(matrix);
        System.out.println(generatedObj);
    }

    public SpiralMatrixTest(int caseId, int[][] matrix, List<Integer> expectedObj) {
        super(caseId, expectedObj);
        this.matrix = matrix;
    }

    @Parameters
    public static Collection<Object[]> generateParameters() {
        return Arrays.asList(new Object[][]{
                        new Object[]{1, new int[][]{{1, 2, 3, 4}, {8, 7, 6, 5}}, Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8)},
                        new Object[]{2, new int[][]{{1, 2, 3, 4}}, Arrays.asList(1, 2, 3, 4)},
                        new Object[]{3, new int[][]{{1}, {2}, {3}, {4}}, Arrays.asList(1, 2, 3, 4)},
                        new Object[]{4, new int[][]{{1}}, Arrays.asList(1)}
                }
        );
    }
}
