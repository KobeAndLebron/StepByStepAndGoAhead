package lettcode.easy;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.gohead.shared.test.ParentTest;

public class SumOfIntegerTest extends ParentTest<Integer>{
	private int i;
	private int j;
	
	public SumOfIntegerTest(int caseId, Integer expectedObj, int i, int j){
		super(caseId, true, expectedObj);
		this.i = i;
		this.j = j;
	}
	
	@Parameters
	public static Collection<Object[]> generateParameteres(){
		return Arrays.asList(new Object[][]{
			new Object[]{1, 110, 10, 100},
			new Object[]{2, 11, 10, 1},
			new Object[]{3, 0, 1, -1}
		});
	}
	
	@Test
	public void test(){
		setDebug(-1);
		if(!isIgnored){
			generatedObj = new SumOfIntegers().getSum(i, j);
		}
	}
}
