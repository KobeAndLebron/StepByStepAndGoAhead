package lettcode.medium;

import com.gohead.shared.test.ParentTest;
import lettcode.ListNode;
import org.junit.Assert;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by chenjingshuai on 19-3-25.
 */
public class RemoveDuplicatesFromSortedListTest extends ParentTest<ListNode> {
    private ListNode head;

    public RemoveDuplicatesFromSortedListTest(int caseId, ListNode expectedObj, ListNode head) {
        super(caseId, expectedObj);
        this.head = head;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> generateParameters(){
        return Arrays.asList(new Object[][]{
                new Object[]{1, ListNode.generateListNode(2, 3, 4), ListNode.generateListNode(1, 1, 2, 3, 4)},
                new Object[]{2, ListNode.generateListNode(2, 3), ListNode.generateListNode(1, 1, 1, 2, 3)},
                new Object[]{3, ListNode.generateListNode(1, 2, 5), ListNode.generateListNode(1, 2, 3, 3, 4, 4, 5)},
                new Object[]{4, null, ListNode.generateListNode(1, 1)},
            }
        );
    }

    @Override
    protected void test() {
        ListNode headCopy = ListNode.copyListNode(head);

        generatedObj = new RemoveDuplicatesFromSortedList().deleteDuplicates(head);
        ListNode generateObj1 = new RemoveDuplicatesFromSortedList().deleteDuplicates1(headCopy);
        Assert.assertEquals(generatedObj, generateObj1);
        System.out.println(generatedObj);
    }
}
