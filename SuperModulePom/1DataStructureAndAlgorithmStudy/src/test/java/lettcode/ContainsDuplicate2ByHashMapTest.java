package lettcode;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ContainsDuplicate2ByHashMapTest {
	private int nums[];
	private int k;
	private boolean ifExists;
	private ContainsDuplicate2HashMap c;
	
	public ContainsDuplicate2ByHashMapTest(int[] nums, int k, boolean ifExists){
		this.nums = nums;
		this.k = k;
		this.ifExists = ifExists;
	}
	
	@Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {     
        	{new int[]{1, 2, 3}, 2, false},
        	{new int[]{1, 2, 2}, 2, true},
        	{new int[]{1, 2, 3, 4, 2}, 2, false},
        	{new int[]{1, 2, 3, 4, 2}, 3, true},
        	{new int[]{1, 2, 3}, 2, false},
        	{new int[]{-1, -1}, 1, true}
        });
    }
    
    @Before
    public void init(){
    	c = new ContainsDuplicate2HashMap();
    }
    
    @Test
    public void test(){
    	assertEquals(this.ifExists, c.containsNearbyDuplicate(nums, k));
    }
}
