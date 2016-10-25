package lettcode;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.gohead.shared.test.ParentTest;

public class SingleNumberTest extends ParentTest<int[]>{
	private int[] inputArray; 
	public SingleNumberTest(int caseId, int[] expectedObj, int[] inputArray) {
		super(caseId, expectedObj);
		this.inputArray = inputArray;
	}

	@Parameters
	public static Collection<Object[]> generateParameters(){
		return Arrays.asList(new Object[][]{
			new Object[]{1, new int[]{-1895772685, -967931676}, 
					new int[]{1403617094,-490450406,-1756388866,-967931676,1878401007,1878401007,-74743538,1403617094,-1756388866,
							-74743538,-490450406,-1895772685}},
			new Object[]{2, new int[]{0,1}, new int[]{0, 1}}
		});
	}
	
	@Test
	public void test1(){
		setDebug(-1);
		if(!isIgnored){
			Arrays.sort(expectedObj);
			generatedObj = new SingleNumber().singleNumber(inputArray);
			Arrays.sort(generatedObj);
		}
	}
	
}
