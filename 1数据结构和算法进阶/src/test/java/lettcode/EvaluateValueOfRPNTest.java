package lettcode;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class EvaluateValueOfRPNTest {
	private String[] input;
	private int expectedNum;
	
	 public EvaluateValueOfRPNTest(String[] input, int expectedNum){
		 this.input = input;
		 this.expectedNum = expectedNum;
     }
	
	@Parameters
	public static Collection<Object[]> generateData(){
		return Arrays.asList(new Object[][] {
			            { new String[]{"2", "1", "+", "3", "*"}, 9 },
			            { new String[]{"4", "13", "5", "/", "+"}, 6 },
			           /* { new String[]{"10","6","9","3","+","-11","","/","","17","+","5","+"}, 12 },*/
			       });
	}
	@Test
	public void test1(){
		Assert.assertEquals(expectedNum, new EvaluateValueOfRPN().evalRPN(input));
		System.out.println(new EvaluateValueOfRPN().evalRPN(input));
	}
}
