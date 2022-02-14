package lettcode;

import linkedlist.CycleLinkedList;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author chenjingshuai
 * @date 16-11-30
 */
public class ListNode {
    public int val;
    public ListNode next;
    public ListNode() { }
    public ListNode(int x) { val = x; }

    private ListNode(int... array) {
        if (array != null) {
            ListNode listNode = this;
            for (int i = 0; i < array.length; i++) {
                listNode.val = array[i];
                if (i != array.length - 1) {
                    listNode.next = new ListNode();
                    listNode = listNode.next;
                }
            }
        }
    }

    public static ListNode generateListNode(int... array) {
        ListNode listNode = null;
        if (array != null && array.length != 0) {
            listNode = new ListNode(array);
        }
        return listNode;
    }

    /**
     * 复制链表.
     *
     * @param listNode
     * @return
     */
    public static ListNode copyListNode(ListNode listNode) {
        ListNode pseudoNode = new ListNode();
        ListNode head = pseudoNode;

        while (listNode != null) {
            pseudoNode.next = new ListNode(listNode.val);

            pseudoNode = pseudoNode.next;
            listNode = listNode.next;
        }

        return head.next;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode listNode = this;
        // 新增对环位置的处理
        Set<ListNode> accessSet = new HashSet<>();
        while (listNode != null) {
            ListNode temp = listNode.next;
            if (accessSet.contains(listNode)) {
                sb.append(";  环的位置--->>>");
                sb.append(listNode.val);
                break;
            }
            sb.append(listNode.val);
            if (temp != null) {
                sb.append("->");
            }
            accessSet.add(listNode);
            listNode = temp;
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof ListNode) {
            ListNode listNode1 = (ListNode) obj;
            ListNode listNode2 = this;
            while (listNode1 != null && listNode2 != null) {
                if (listNode1.val != listNode2.val) {
                    return false;
                }
                listNode1 = listNode1.next;
                listNode2 = listNode2.next;
            }
            return listNode1 == null && listNode2 == null;
        } else {
            return false;
        }
    }
}
