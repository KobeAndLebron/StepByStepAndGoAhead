package linkedlist;

import lettcode.ListNode;

/**
 *
 * 1. https://leetcode.com/problems/reverse-linked-list/
 * 反转一个单链表。
 示例:

    输入: 1.2.3.4.5.null
    输出: 5.4.3.2.1.null
 *
 * 2. https://leetcode.com/problems/swap-nodes-in-pairs/
 *
 *
 * @author chenjingshuai
 * @date 19-3-7
 */
public class ReverseList {
    /**
     * 非递归方法.
     * @param listNode
     * @return
     */
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
            // 不对最后的null节点最后设置next的操作, 此时的listNode为新列表的头结点.
            if (preListNode != null) {
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
        if(head == null || head.next == null) {
            return head;
        }

        ListNode rHead = reverseListRecursively1(head.next);
        // head.next此刻指向head后面的链表的尾节点
        // head.next.next = head把head节点放在了尾部
        head.next.next = head;
        head.next = null;
        return rHead;
    }

    /**
     *
     *  Example:

        Given 1->2->3->4, you should return the list as 2->1->4->3.
     * @param head
     * @return
     */
    public ListNode reversePairsRecursively(ListNode head) {
        // 至少有两个节点.
        if (head != null && head.next != null) {
            // 第二个节点.
            ListNode secondNode = head.next;
            // 第三个节点.
            ListNode thirdNode = secondNode.next;
            // 第二个节点指向第一个节点.
            secondNode.next = head;

            // 现在的头结点变成之前的第二个节点.
            head = secondNode;
            // 现在的第二个节点变成之前的头节点.
            secondNode = head.next;

            // 翻转后面的节点.
            thirdNode = reversePairsRecursively(thirdNode);
            // 第二个节点指向第三个节点.
            secondNode.next = thirdNode;
        }
        return head;
    }

}
