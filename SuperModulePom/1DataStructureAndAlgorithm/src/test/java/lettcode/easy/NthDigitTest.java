package lettcode.easy;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by chenjingshuai on 19-4-16.
 */
public class NthDigitTest {

    @Test
    public void findNthDigit() {
        assert1(1, 22);
        assert1(8, 2886);
        assert1(5, 5);
        assert1(1, 1);
        assert1(1, 10);
    }

    private void assert1(int expectNumber, int input) {
        NthDigit nthDigit = new NthDigit();
        Assert.assertEquals(expectNumber, nthDigit.findNthDigit(input));
        Assert.assertEquals(expectNumber, nthDigit.findNthDigit1(input));
    }
    
}