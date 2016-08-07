package lettcode.easy;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.gohead.shared.test.ParentTest;

@RunWith(Parameterized.class)
public class ReverseStringTest extends ParentTest<String>{
	protected String inputArr;
	
	public ReverseStringTest(int caseId, String expectedObj, String intputArr) {
		super(caseId, expectedObj);
		this.inputArr = intputArr;
	}

	@Parameters
	public static Collection<Object[]> generateParameters(){
		return Arrays.asList(new Object[]{1, "a", "a"},new Object[]{2, "ba", "ab"});
	}
	
	@Test
	public void testTime(){
		setDebug(-1);
		if(!isIgnored){
			generatedObj = new ReverseString().reverseString1(inputArr);
		}
	}
}

