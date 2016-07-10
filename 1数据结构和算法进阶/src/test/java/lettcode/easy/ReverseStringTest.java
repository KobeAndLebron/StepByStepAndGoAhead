package lettcode.easy;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class ReverseStringTest {
	private String str;
	
    public ReverseStringTest(String str) {
		this.str = str;
	}
	
	@Parameters
	public static Collection<Object[]> generateParameters(){
		/*return Arrays.asList(new Object[][]{
			new Object[]{"aaaaaabbbbbbbbbbbcccccccccccccdddddddddd"},
		});*/
		return Arrays.asList(new Object[]{"a"},new Object[]{"ab"},new Object[]{null});
	}
	
	@Test
	public void testTime(){
		System.out.println(new ReverseString().reverseString(this.str));
	}
}

