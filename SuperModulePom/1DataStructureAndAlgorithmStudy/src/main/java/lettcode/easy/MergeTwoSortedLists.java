package lettcode.easy;

import lettcode.ListNode;

/**
 * Created by chenjingshuai on 16-11-30.
 */
public class MergeTwoSortedLists {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return null;
        }
        ListNode node1 = l1;
        ListNode node2 = l2;
        ListNode newList = new ListNode(0);
        ListNode temp = newList;
        while (node1 != null || node2 != null) {
            int val;
            if (node1 == null || (node2 != null && node1.val > node2.val)) {
                val = node2.val;
                node2 = node2.next;
            } else {
                val = node1.val;
                node1 = node1.next;
            }
            temp.val = val;

            /*newList.val = val;
            newList.next = new ListNode(0);*/

            if (node1 != null || node2 != null) {
                temp.next = new ListNode(0);
                temp = temp.next;
            }
        }
        return newList;
    }
}
