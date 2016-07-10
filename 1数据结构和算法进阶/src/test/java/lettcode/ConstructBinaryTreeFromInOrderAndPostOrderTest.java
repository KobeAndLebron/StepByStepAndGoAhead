package lettcode;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import static org.junit.Assert.*;

@RunWith(value=org.junit.runners.Parameterized.class)
public class ConstructBinaryTreeFromInOrderAndPostOrderTest {
	
	@Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
        			{new int[]{3, 2, 1}, new int[]{3, 2, 1}},
        			{new int[]{4, 3, 2, 1}, new int[]{4, 3, 2, 1}},
        			{new int[]{1}, new int[]{1}}
        });
    }
    
    private int[] inorder;
    private int[] postorder;
    private ConstructBinaryTreeFromInOrderAndPostOrder cbt = new ConstructBinaryTreeFromInOrderAndPostOrder();
    
    public ConstructBinaryTreeFromInOrderAndPostOrderTest(int[] inorder, int[] postorder){
    	this.inorder = inorder;
    	this.postorder = postorder;
    }
    
	@Test
	public void test(){
		assertNotNull(cbt.buildTree(inorder, postorder));
	}
}
