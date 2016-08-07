package lettcode;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;

import com.gohead.shared.test.ParentTest;

@RunWith(value=org.junit.runners.Parameterized.class)
public class ConstructBinaryTreeFromInOrderAndPostOrderTest extends ParentTest<Object>{
	
	public ConstructBinaryTreeFromInOrderAndPostOrderTest(int caseId, Object expectedObj, int[] inorder, int[] postorder) {
		super(caseId, expectedObj);
		this.inorder = inorder;
		this.postorder = postorder;
	}


	@Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
        			{1, OBJ,new int[]{3, 2, 1}, new int[]{3, 2, 1}},
        			{2, OBJ, new int[]{4, 3, 2, 1}, new int[]{4, 3, 2, 1}},
        			{3, OBJ, new int[]{1}, new int[]{1}}
        });
    }
    
    private int[] inorder;
    private int[] postorder;
    private ConstructBinaryTreeFromInOrderAndPostOrder cbt = new ConstructBinaryTreeFromInOrderAndPostOrder();
    
    
	@Test
	public void test(){
		setDebug(-1);
		if(!isIgnored){
			generatedObj = cbt.buildTree(inorder, postorder);
		}
	}
}
