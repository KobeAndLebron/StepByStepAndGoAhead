package linkedlist;

import lettcode.ListNode;

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
    // 方法1: 通过HashSet, 空间复杂度为O(N), 时间复杂度为O(N)
    public boolean hasCycle(ListNode head) {
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

    // 通过快慢指针, 空间复杂度为O(1), 时间复杂度为O(N)
    public boolean hasCycle1(ListNode head) {
        if (head == null) {
            return false;
        }

        // 快指针, 一次走两步
        ListNode fast = head.next;
        // 慢指针, 一次走一步.
        ListNode slow = head;

        // 当两者相等时, 则有环; 快指针为null时, 则无环.
        while (fast != null && fast.next != null) {
            if (fast == slow) {
                return true;
            } else {
                fast = fast.next.next;
                slow = slow.next;
            }
        }

        return false;
    }
}
