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
public class ReverseListTest extends ParentTest<ListNode> {
    private ListNode headNode;
    private ListNode headNode1;
    private ListNode headNode2;

    public ReverseListTest(int caseId, ListNode expectedObj, ListNode headNode, ListNode headNode1, ListNode headNode2) {
        super(caseId, expectedObj);
        this.headNode = headNode;
        this.headNode1 = headNode1;
        this.headNode2 = headNode2;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][]{
            new Object[]{1, ListNode.generateListNode(4, 3, 2, 1), ListNode.generateListNode(1, 2, 3, 4),
                ListNode.generateListNode(1, 2, 3, 4), ListNode.generateListNode(1, 2, 3, 4)},
            new Object[]{2, null, null, null, null},
        });
    }

    @Override
    protected void test() {
        generatedObj = new ReverseList().reverseList(headNode);
        ListNode generateObj1 = new ReverseList().reverseListRecursively(headNode1);
        ListNode generateObj2 = new ReverseList().reverseListRecursively1(headNode2);
        Assert.assertEquals(generateObj1, generatedObj);
        Assert.assertEquals(generateObj2, generatedObj);
    }
}
