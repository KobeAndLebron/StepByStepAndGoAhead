package linkedlist;

import lettcode.ListNode;

/**
 * 反转一个单链表。
 示例:

    输入: 1.2.3.4.5.null
    输出: 5.4.3.2.1.null
 * Created by chenjingshuai on 19-3-7.
 */
public class ReverseList {
    public ListNode reverseList(ListNode listNode) {
        ListNode pre = null;
        ListNode cur = listNode;
        while (cur != null) {
            ListNode after = cur.next;
            cur.next = pre;
            pre = cur;
            cur = after;
        }

        return pre;
    }

    public ListNode reverseListRecursively(ListNode listNode) {
        if (listNode != null) {
            ListNode next = listNode.next;
            ListNode preListNode = reverseListRecursively(next);
            // 等价于next != null
            if (preListNode != null) { // 不对最后的null节点最后设置next的操作, 此时的listNode为新列表的头结点.
                next.next = listNode;
                listNode.next = null;
                return preListNode;
            } else {
                return listNode;
            }
        } else {
            return null;
        }

    }

    public ListNode reverseListRecursively1(ListNode head) {
        // 递归终止条件
        if(head == null || head.next == null)
            return head;

        ListNode rhead = reverseListRecursively1(head.next);
        // head.next此刻指向head后面的链表的尾节点
        // head.next.next = head把head节点放在了尾部
        head.next.next = head;
        head.next = null;
        return rhead;
    }

    /**
     *  https://leetcode.com/problems/swap-nodes-in-pairs/
     *
     *  Example:

        Given 1->2->3->4, you should return the list as 2->1->4->3.
     * @param head
     * @return
     */
    public ListNode reversePairsRecursively(ListNode head) {
        if (head != null && head.next != null) { // 至少有两个节点.
            ListNode secondNode = head.next; // 第二个节点.
            ListNode thirdNode = secondNode.next; // 第三个节点.
            secondNode.next = head; // 第二个节点指向第一个节点.

            head = secondNode; // 现在的头结点变成之前的第二个节点.
            secondNode = head.next; // 现在的第二个节点变成之前的头节点.

            thirdNode = reversePairsRecursively(thirdNode);// 翻转后面的节点.
            secondNode.next = thirdNode; // 第二个节点指向第三个节点.
        }
        return head;
    }

}
