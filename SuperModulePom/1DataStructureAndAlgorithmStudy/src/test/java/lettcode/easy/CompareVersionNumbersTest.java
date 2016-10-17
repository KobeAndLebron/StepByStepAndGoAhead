package lettcode.easy;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.gohead.shared.test.ParentTest;

public class CompareVersionNumbersTest extends ParentTest<Integer>{
	private String version1;
	private String version2;
	
	public CompareVersionNumbersTest(int caseId, Integer expectedObj, String version1, String version2) {
		super(caseId, expectedObj);
		this.version1 = version1;
		this.version2 = version2;
	}

	@Parameters
	public static Collection<Object[]> generateParameters(){
		return Arrays.asList(new Object[][]{
			new Object[]{1, 1, "2.0", "1.1"},
			new Object[]{2, -1, "1.2", "2.1"},
			new Object[]{3, 0, "11.22.1", "11.22.1"},
			new Object[]{4, 1, "121.22.1", "11.22.1"},
			new Object[]{5, -1, "121.22.1", "121.23.1"},
			new Object[]{6, -1, "121.0022.1", "121.0023.1"},
			new Object[]{7, 0, "", ""},
			new Object[]{8, -1, "1.1.1", "1.1.1.1"}
		});
	}
	
	@Test
	public void commenTest(){
		setDebug(-1);
		if(!isIgnored){
			this.generatedObj = new CompareVersionNumbers().compareVersion(version1, version2);
		}
	}
	
	/*@Test 
	public void testEmptyStr(){
		Integer i = Integer.parseInt("");
		System.out.println(i);
	}*/
}
