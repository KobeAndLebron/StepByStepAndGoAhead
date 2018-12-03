package lettcode.easy;

import com.gohead.shared.test.ParentTest;
import lettcode.ListNode;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Created by chenjingshuai on 16-11-30.
 */
public class MergeTwoSortedListsTest extends ParentTest<ListNode> {
    ListNode listNode1;
    ListNode listNode2;
    private static final MergeTwoSortedLists MERGE_TWO_SORTED_LISTS = new MergeTwoSortedLists();

    public MergeTwoSortedListsTest(int[] listNode1, int[] listNode2, int[] expectedObj) {
        super(ListNode.generateListNode(expectedObj));
        this.listNode1 = ListNode.generateListNode(listNode1);
        this.listNode2 = ListNode.generateListNode(listNode2);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> generateParameters() {
        return Arrays.asList(new Object[][]{
                new Object[]{new int[]{0, 1, 2, 3, 4}, new int[]{0, 1, 2, 3, 4}, new int[]{0, 0, 1, 1, 2, 2, 3, 3, 4, 4}},
                new Object[]{new int[]{0}, new int[]{0}, new int[]{0, 0}},
                new Object[]{new int[]{0}, new int[]{}, new int[]{0}},
                new Object[]{new int[]{0}, null, new int[]{0}},

        });
    }

    @Override
    protected void test() {
        generatedObj = MERGE_TWO_SORTED_LISTS.mergeTwoLists(listNode1, listNode2);
    }
}
