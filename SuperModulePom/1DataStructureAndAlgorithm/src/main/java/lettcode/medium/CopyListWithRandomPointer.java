package lettcode.medium;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/copy-list-with-random-pointer/
 *
 *   A linked list is given such that each node contains an additional random pointer which could
 * point to any node in the list or null.
 *
 * Created by chenjingshuai on 19-3-13.
 */
public class CopyListWithRandomPointer {
    /**
     * Node with random pointer.
     */
    private class Node {
        public int val;
        public Node next;
        public Node random;

        public Node() {}

        public Node(int _val,Node _next,Node _random) {
            val = _val;
            next = _next;
            random = _random;
        }
    }

    /**
     * 空间复杂度为O(n).
     *
     * @param head
     * @return
     */
    public Node copyRandomList(Node head) {
        if(head == null) {
            return null;
        }

        Map<Node, Node> hashMap = new HashMap<>();

        Node newHead = new Node(); // 新链表的头结点.
        Node temp = newHead; // 暂存新链表头结点.

        while (head != null) {
            newHead.val = head.val;

            hashMap.put(head, newHead);

            Node oldNext = head.next;
            if (oldNext == null) {
                newHead.next = null;
            } else {
                if (hashMap.get(oldNext) != null) {
                    newHead.next = hashMap.get(oldNext);
                } else {
                    Node newNext = new Node();
                    newNext.val = oldNext.val;

                    newHead.next = newNext;

                    hashMap.put(oldNext, newNext);
                }
            }

            Node oldRandom = head.random;
            if(oldRandom == null) {
                newHead.random = null;
            } else if (hashMap.get(oldRandom) != null) {
                newHead.random = hashMap.get(oldRandom);
            } else {
                Node newRandom = new Node();
                newRandom.val = oldRandom.val;

                newHead.random = newRandom;

                hashMap.put(oldRandom, newRandom);
            }

            head = head.next;
            newHead = newHead.next;
        }

        return temp;

    }


    /**
     * 空间复杂度为常量的解法.
     *
     * @param head
     * @return
     */
    public Node copyRandomListWithConstantSpaceComplexity(Node head) {
        Node iter = head;

        while (iter != null) {
            Node node = new Node();
            node.val = iter.val;

            node.next = iter.next;
            iter.next = node;
            iter = node.next;
        }

        iter = head;
        while (iter != null) {
            if (iter.random != null) {
                iter.next.random = iter.random.next;
            }
            iter = iter.next.next;
        }

        Node newHead = null;
        iter = head;
        while (iter != null) {
            Node oldCur = iter.next;
            Node iterNext = oldCur.next;

            if (newHead == null) {
                newHead = oldCur;
            }

            iter.next = iterNext;
            if (iterNext != null) {
                oldCur.next = iterNext.next;
            } else {
                oldCur.next = null;
            }

            iter = iterNext;
        }

        return newHead;
    }
}
