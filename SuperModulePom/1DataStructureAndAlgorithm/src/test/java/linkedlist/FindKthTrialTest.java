package linkedlist;

import com.gohead.shared.test.ParentTest;
import lettcode.ListNode;
import org.junit.Assert;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by chenjingshuai on 19-3-7.
 */
public class FindKthTrialTest extends ParentTest<ListNode> {
    private ListNode headNode;
    private int k;


    public FindKthTrialTest(int caseId, ListNode expectedObj, ListNode headNode, int k) {
        super(caseId, expectedObj);
        this.headNode = headNode;
        this.k = k;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> generateData(){
        return Arrays.asList(new Object[][]{
            new Object[]{1, new ListNode(1), ListNode.generateListNode(1, 2, 3, 4), 4},
            new Object[]{2, null, ListNode.generateListNode(1, 2, 3, 4), 5},
            new Object[]{3, new ListNode(4), ListNode.generateListNode(1, 2, 3, 4), 1},
        });
    }

    @Override
    protected void test() {
        ListNode actualListNode = new FindKthTrial().findKthTrial(headNode, k);
        ListNode actualListNode1 = new FindKthTrial().findKthTrialRecursively(headNode, k);
        if (actualListNode == null) {
            Assert.assertNull(actualListNode1);
            Assert.assertNull(expectedObj);
        } else {
            Assert.assertEquals(expectedObj.val, actualListNode.val);
            Assert.assertEquals(expectedObj.val, actualListNode1.val);
        }
    }

    @Override
    protected void setSelfJudged() {
        judgeBySelf();
    }
}
