package lettcode.medium;

import lettcode.ListNode;

/**
 *  https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/
 *

    Given a sorted linked list, delete all nodes that have duplicate numbers, leaving only distinct numbers from the
   original list.

    Example 1:

    Input: 1->2->3->3->4->4->5
    Output: 1->2->5
    Example 2:

    Input: 1->1->1->2->3
    Output: 2->3
 * Created by chenjingshuai on 19-3-25.
 */
public class RemoveDuplicatesFromSortedList {
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        // 伪头结点.
        ListNode pseudoNode = new ListNode(head.val - 1);
        pseudoNode.next = head;

        ListNode preIterator = pseudoNode;
        ListNode curIterator = pseudoNode.next;
        while (curIterator.next != null) {
            boolean isDuplicate = false;
            do {
                if (curIterator.next.val == curIterator.val) {
                    curIterator = curIterator.next;
                    isDuplicate = true;
                } else {
                    break;
                }
            } while (curIterator.next != null);

            if (isDuplicate) { // 有重复的节点存在.
                preIterator.next = curIterator.next;
                curIterator = curIterator.next;
                if (curIterator == null) {
                    break;
                }
            } else {
                preIterator = curIterator;
                curIterator = preIterator.next;
            }
        }

        return pseudoNode.next;
    }

    /**
     *　简洁版.
     * @param head
     * @return
     */
    public ListNode deleteDuplicates1(ListNode head) {
        if (head == null || head.next == null) { // 没有节点或只有一个节点.
            return head;
        }

        // 伪头结点.
        ListNode pseudoNode = new ListNode(head.val - 1);
        pseudoNode.next = head;

        ListNode preIterator = pseudoNode;
        ListNode curIterator = pseudoNode.next;

        while (curIterator != null) {
            while (curIterator.next != null && curIterator.next.val == curIterator.val) {
                curIterator = curIterator.next;
            }

            if (preIterator.next == curIterator) { // 没有重复的节点存在.
                preIterator = curIterator;
            } else {
                preIterator.next = curIterator.next;
            }
            curIterator = curIterator.next;
        }

        return pseudoNode.next;
    }

}
