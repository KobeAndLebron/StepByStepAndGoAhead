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
    /**
     * {@linkplain ReverseList#reversePairsRecursively(ListNode)} 的期望结果.
     */
    private ListNode expectObj2;

    public ReverseListTest(int caseId, ListNode expectedObj, ListNode headNode, ListNode expectedObj2) {
        super(caseId, expectedObj);
        this.headNode = headNode;
        this.expectObj2 = expectedObj2;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> generateData() {
        return Arrays.asList(new Object[][]{
            new Object[]{1, ListNode.generateListNode(4, 3, 2, 1), ListNode.generateListNode(1, 2, 3, 4),
                ListNode.generateListNode(3, 4, 1, 2)},
            new Object[]{2, ListNode.generateListNode(4, 3, 2), ListNode.generateListNode(2, 3, 4),
                ListNode.generateListNode(3, 4, 2)},
            new Object[]{3, null, null, null},
            new Object[]{4, ListNode.generateListNode(1), ListNode.generateListNode(1), ListNode.generateListNode(1)}
        });
    }

    @Override
    protected void test() {
        ListNode headNodeCopy = ListNode.copyListNode(headNode);
        ListNode headNodeCopy1 = ListNode.copyListNode(headNode);

        generatedObj = new ReverseList().reverseList(headNode);
        ListNode generateObj1 = new ReverseList().reverseListRecursively(headNodeCopy);
        ListNode generateObj2 = new ReverseList().reverseListRecursively1(headNodeCopy1);
        Assert.assertEquals(generateObj1, generatedObj);
        Assert.assertEquals(generateObj2, generatedObj);

        ListNode generateObj3 = new ReverseList().reversePairsRecursively(generateObj1);
        Assert.assertEquals(expectObj2, generateObj3);
    }
}
