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
public class AddTwoListTest extends ParentTest<ListNode> {
    private ListNode firstNode;
    private ListNode secondNode;

    public AddTwoListTest(int caseId, ListNode expectedObj, ListNode firstNode, ListNode secondNode) {
        super(caseId, expectedObj);
        this.firstNode = firstNode;
        this.secondNode = secondNode;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][]{
            new Object[]{1, ListNode.generateListNode(10, 4, 6, 8),
                ListNode.generateListNode(9, 2, 3, 4), ListNode.generateListNode(1, 2, 3, 4)},
            new Object[]{2, ListNode.generateListNode(2, 4, 6, 8, 5, 6, 7, 8),
                ListNode.generateListNode(1, 2, 3, 4), ListNode.generateListNode(1, 2, 3, 4, 5, 6, 7, 8)},
            new Object[]{3, ListNode.generateListNode(1, 2, 3, 4, 1, 2, 3, 4),
                ListNode.generateListNode(0, 0, 0, 0, 1, 2, 3, 4), ListNode.generateListNode(1, 2, 3, 4)},
            new Object[]{3, ListNode.generateListNode(9, 0, 7),
                ListNode.generateListNode(3, 1, 5), ListNode.generateListNode(5, 9, 2)},
        });
    }

    @Override
    protected void test() {
        generatedObj = new AddTwoList().addTwoList(firstNode, secondNode);
        ListNode generateObj1 = new AddTwoList().addTwoList(firstNode, secondNode);
        Assert.assertEquals(generateObj1, generatedObj);
    }

}
