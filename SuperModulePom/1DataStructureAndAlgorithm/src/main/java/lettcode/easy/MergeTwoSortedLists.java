package lettcode.easy;

import lettcode.ListNode;

/**
 *  问题描述
    题目：将两个有序链表合并为一个新的有序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。

    示例： 输入： 1->2->4, 1->3->4 输出： 1->1->2->3->4->4
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

    public ListNode mergeTwoListsRecursively(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null) {
            return null;
        } else if (l1 == null) {
            return l2;
        } else if (l2 == null) {
            return l1;
        } else {
            ListNode listNode = new ListNode();
            // 后面已经合并好的list.
            ListNode afterList;
            if (l1.val <= l2.val) {
                listNode.val = l1.val;
                afterList = mergeTwoListsRecursively(l1.next, l2);
            } else {
                listNode.val = l2.val;
                afterList = mergeTwoListsRecursively(l1, l2.next);
            }
            listNode.next = afterList;

            return listNode;
        }
    }
}
