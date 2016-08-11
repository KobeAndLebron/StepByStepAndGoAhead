package lettcode;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.gohead.shared.test.ParentTest;

@RunWith(Parameterized.class)
public class EvaluateValueOfRPNTest extends ParentTest<Integer>{
	private String[] input;

	public EvaluateValueOfRPNTest(int caseId, Integer expectedObj, String[] inputArr) {
		super(caseId, expectedObj);
		this.input = inputArr;
	}
	
	@Parameters
	public static Collection<Object[]> generateData(){
		return Arrays.asList(new Object[][] {
			            new Object[]{1, 9,  new String[]{"2", "1", "+", "3", "*"}},
			            new Object[]{2, 6, new String[]{"4", "13", "5", "/", "+"}},
			       });
	}
	@Test
	public void test1(){
		setDebug(-1);
		if(!isIgnored){
			generatedObj = new EvaluateValueOfRPN().evalRPN(input);
		}
	}
}
