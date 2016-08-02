package lettcode.easy;

import org.junit.Test;
import static org.junit.Assert.*;

public class SumOfIntegerTest {
	@Test
	public void test(){
		int i = 10;
		int j = 100;
		assertEquals(i + j, new SumOfIntegers().getSum(i, j));
	}
}
