package linkedlist;

import lettcode.ListNode;
import sun.jvm.hotspot.utilities.Assert;

import java.util.HashSet;
import java.util.Set;

/**
 * 1.Given head, the head of a linked list, determine if the linked list has a cycle in it.
 * Return true if there is a cycle in the linked list. Otherwise, return false.
 *
 * 2. Given the head of a linked list, return the node where the cycle begins. If there is no cycle, return null.
 *
 * 两者相比, 方法2更好.
 */
public class CycleLinkedList {
    // 问题1的第一种解法: 通过HashSet, 空间复杂度为O(N), 时间复杂度为O(N)
    public boolean hasCycle1_1(ListNode head) {
        if (head == null) {
            return false;
        }
        Set<ListNode> hashSet = new HashSet<>();

        ListNode iterator = head;
        while (iterator != null) {
            if (!hashSet.contains(iterator)) {
                hashSet.add(iterator);
            } else {
                return true;
            }
            iterator = iterator.next;
        }

        return false;
    }

    // 问题1的第二种解法: 通过快慢指针, 空间复杂度为O(1), 时间复杂度为O(N)
    public boolean hasCycle1_2(ListNode head) {
        ListNode slow = head;
        ListNode fast = head == null ? null : head.next;
        while (slow != null && fast != null) {
            if (slow == fast) {
                return true;
            }
            slow = slow.next;
            fast = fast.next == null ? null : fast.next.next;
        }

        return false;
    }

    // 问题2解法, 假设相遇时, slow走的距离是S, 那么fast将走的距离是2S.
    public ListNode hasCycle2_1(ListNode head) {
        ListNode slow = head;
        // NOTE: 1. 由于要保证fast和slow相遇时, S = 2S, 所以起点要相同.
        ListNode fast = head;
        ListNode meetNode = null;
        while (slow != null && fast != null) {
            // NODE: 2. if else要放到走步数后面, 否则初始状态下head == head, 相遇节点将是head.
            slow = slow.next;
            fast = fast.next == null ? null : fast.next.next;
            if (slow == fast) {
                meetNode = slow;
                break;
            }
        }

        // NOTE3: 推导过程
        // 假设起始点到环位置的距离为a, 相遇点到环位置的距离为c, 环位置到相遇点的距离为b, 即b+c等于环的周长.
        // slow走的距离: a + b
        // fast走的距离: a + b + c + b + n(b+c)
        // 由 2slow = fast
        // 得 2a + 2b = a + b + c + b + n(b+c)
        // a = c + n(b+c)
        // 所以当从起点出发走a的距离后, 另一个节点也相遇点走了c + n(b+c), 将在环的位置相遇.

        // 将slow放回起点, meetNode为相遇点, 同时走1步, 相遇点则是环的位置.
        if (meetNode != null) {
            slow = head;
            while (slow != meetNode) {
                slow = slow.next;
                meetNode = meetNode.next;
            }
            return slow;
        } else {
            return null;
        }
    }

    public static void main(String[] args) {
        ListNode listNode = ListNode.generateListNode(0, 1, 2, 2, 4, 5, 6, 7);
        ListNode head = listNode;
        ListNode node2 = null;
        while (head.next != null) {
            if (head.val == 2) { // 第一个2为环的位置.
                node2 = head;
            }
            head = head.next;
        }
        head.next = node2;
        ListNode meetNode = new CycleLinkedList().hasCycle2_1(listNode);
        System.out.println(listNode);
        System.out.println(meetNode.val == 2);
        System.out.println(meetNode);

    }
}
