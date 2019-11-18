package lettcode.medium;

/**
 * https://leetcode.com/problems/lru-cache/
 * O(n)复杂度, 使用单链表实现.
 *
 *  写入:
 *   1. 当元素未满, 直接插入.
 *   2. 当元素满的时候, 会删除时间最久最未使用的元素[列表的头].
 *  读取:
 *    1. 访问/更新元素的时候, 会将对应节点移到列表的尾部.
 *
 *
 * @see LRUWithHashMap
 */
public class LRU {

    public static void main(String[] args) {
        LRUCache cache = new LRUCache(2 /* capacity */);

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


class LRUCache {
    // 伪头结点, headNode = pseudoHeadNode.next
    private Node pseudoHeadNode = new Node();
    private Node tailNode;
    private int capacity;
    private int size;

    class Node {
        int key;
        int value;
        Node next;

        public Node() {
        }

        public Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    public int get(int key) {
        Node findNode = getNode(key);

        return findNode == null ? -1 : findNode.value;
    }

    public Node getNode(int key) {
        if (size == 0) {
            return null;
        }
        Node preNode = pseudoHeadNode;
        Node node = pseudoHeadNode.next;
        while (node != null && node.key != key) {
            preNode = preNode.next;
            node = node.next;
        }
        if (node != null && node.next != null) { // 如果不是最后一个节点, 则将此节点移到到尾部.
            preNode.next = node.next;
            tailNode.next = node;
            node.next = null;
            tailNode = node;
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
                pseudoHeadNode.next = headNode == null ? null : headNode.next;
                headNode = pseudoHeadNode.next;
            }

            Node newTailNode = new Node(key, value);
            if (headNode == null) { // 链表为空, 首次插入元素.
                pseudoHeadNode.next = newTailNode;
            } else { // 链表非空, 直接在尾部加入新元素.
                tailNode.next = newTailNode;
            }
            tailNode = newTailNode;

            size++;
        }

    }

}
