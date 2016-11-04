package lettcode.easy;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.gohead.shared.test.CaseManager;
import com.gohead.shared.test.ParentTest;

@RunWith(Parameterized.class)
public class ReverseStringTest extends ParentTest<String>{
	private String inputArr;
	
	public ReverseStringTest(int caseId, boolean isIgnored, String expectedObj, String intputArr) {
		super(caseId, expectedObj, isIgnored);
		this.inputArr = intputArr;
	}

	@Parameters
	public static Collection<Object[]> generateParameters(){
		return Arrays.asList(CaseManager.getParameterArray("testReverseString.html"));
	}
	
	@Test
	public void test(){
		if(!isIgnored()){
			generatedObj = new ReverseString().reverseString1(inputArr);
		}
	}

    @Override
    protected void setSelfJudged() {
        judgeBySelf();
    }
}

