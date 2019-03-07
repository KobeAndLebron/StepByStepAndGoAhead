package linkedlist;

import lettcode.ListNode;

/**
 *
 * 问题描述
 * 题目：输入一个单链表，输出此链表中的倒数第 K 个节点。（去除头结点，节点计数从 1 开始）
 *
 * Created by chenjingshuai on 19-3-7.
 */
public class FindKthTrial {
    public ListNode findKthTrial(ListNode headNode, int k) {
        if (headNode == null || k <= 0) {
            return null;
        }

        ListNode first = headNode;
        ListNode second = headNode;
        // 第一个节点和第二个节点相差k-1步.
        for (int i = 1; i < k; i++) {
            ListNode next = second.next;
            if (next!= null) {
                second = next;
            } else { // 没有第k个节点.
                return null;
            }
        }

        // 当第二个节点到达链表终点的时候, 由于第一个节点和第二个节点相差k-1步, 所以此时第一个节点就是倒数第k个节点.
        while (second.next != null) {
            second = second.next;
            first = first.next;
        }

        return new ListNode(first.val);
    }

    private int num;
    public ListNode findKthTrialRecursively(ListNode headNode, int k) {
        if (headNode == null || k <= 0) {
            return null;
        }

        ListNode kthNode = findKthTrialRecursively(headNode.next, k);
        if (kthNode != null) {
            return kthNode;
        } else {
            num++;
            if (num == k) {
                return headNode;
            } else {
                return null;
            }
        }
    }

}
