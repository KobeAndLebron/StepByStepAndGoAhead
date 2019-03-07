package linkedlist;

import lettcode.ListNode;

/**
 *
 * 两个用链表代表的整数，其中每个节点包含一个数字。数字存储按照在原来整数中的顺序，使得第一个数字位于链表的结尾。写出一个函数将两个整数相加，用链表形式返回和。
 例如：
 输入：
    3->1->5->null
    5->9->2->null，
 输出：
    9->0->7->null
 *
 * Created by chenjingshuai on 19-3-7.
 */
public class AddTwoList {
    public ListNode addTwoList(ListNode firstListNode, ListNode secondListNode) {
        ListNode addTwoListNode = new ListNode(); // 两个列表相加后的列表.

        if (firstListNode != null || secondListNode != null) { // 有一个列表不为空都继续.
            if (firstListNode == null) { // 第一个链表结束, 此时将第二个链表的剩余部分返回.
                return secondListNode;
            } else if (secondListNode == null) {
                return firstListNode;
            } else {
                ListNode listNode = addTwoList(firstListNode.next, secondListNode.next);
                int i = firstListNode.val + secondListNode.val;
                if (listNode != null) {
                    int carryNumber = listNode.val / 10; // 进位数.
                    addTwoListNode.val = i + carryNumber;
                    listNode.val = listNode.val - (carryNumber * 10);
                } else {
                    addTwoListNode.val = i;
                }
                addTwoListNode.next = listNode;
                return addTwoListNode;
            }
        } else { // 两个列表都为空, 此时返回null.
            return null;
        }
    }

}
