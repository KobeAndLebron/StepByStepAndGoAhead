package lettcode.medium;

import java.util.HashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/lru-cache/
 * O(1)复杂度, 使用Map+双链表实现.
 *
 * @see LRU
 */
public class LRUWithHashMap {

    public static void main(String[] args) {
        OptimizedLRUCache cache = new OptimizedLRUCache(2 /* capacity */);

        cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));       // returns 1
        cache.put(3, 3);    // evicts key 2
        System.out.println(cache.get(2));       // returns -1 (not found)
        cache.put(4, 4);    // evicts key 1
        System.out.println(cache.get(1));       // returns -1 (not found)
        System.out.println(cache.get(3));       // returns 3
        System.out.println(cache.get(4));       // returns 4


    }
}


class OptimizedLRUCache {
    // 伪头结点, headNode = pseudoHeadNode.next
    private Node pseudoHeadNode = new Node();
    // 伪尾节点, tailNode = pseudoTailNode.pre
    private Node pseudoTailNode = new Node();
    private Map<Integer, Node> cache = new HashMap<>();
    private int capacity;
    private int size;

    class Node {
        int key;
        int value;
        Node next;
        Node pre;

        public Node() {
        }

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public OptimizedLRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        Node findNode = getNode(key);

        return findNode == null ? -1 : findNode.value;
    }

    public Node getNode(int key) {
        Node node = cache.get(key);

        if (node != null && node.next != pseudoTailNode) { // 如果不是最后一个节点, 则将此节点移到到尾部.
            Node preNode = node.pre;
            preNode.next = node.next;
            node.next.pre = preNode;

            pseudoTailNode.pre.next = node;
            node.pre = pseudoTailNode.pre;
            node.next = pseudoTailNode;
            pseudoTailNode.pre = node;
        }

        return node;
    }

    public void put(int key, int value) {
        Node findNode = getNode(key);
        if (findNode != null) { // 设置新值.
            findNode.value = value;
        } else { // 新增元素.
            Node headNode = pseudoHeadNode.next;
            if (size >= capacity) { // 删除头结点.
                if (headNode != null) {
                    if (headNode.next != pseudoTailNode) {
                        pseudoHeadNode.next = headNode.next;
                        headNode.next.pre = pseudoHeadNode;
                    } else {
                        pseudoHeadNode.next = null;
                        pseudoTailNode.pre = null;
                    }
                    cache.remove(headNode.key);
                }
                headNode = pseudoHeadNode.next;
            }

            Node newTailNode = new Node(key, value);
            cache.put(key, newTailNode);
            if (headNode == null) { // 链表为空, 首次插入元素.
                pseudoHeadNode.next = newTailNode;
                newTailNode.pre = pseudoHeadNode;
            } else {
                Node tailNode = pseudoTailNode.pre;
                tailNode.next = newTailNode;
                newTailNode.pre = tailNode;
            }
            newTailNode.next = pseudoTailNode;
            pseudoTailNode.pre = newTailNode;

            size++;
        }

    }

}
