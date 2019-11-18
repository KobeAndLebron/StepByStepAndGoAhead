package lettcode.medium;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * https://leetcode.com/problems/lru-cache/
 *
 * LRU的LinkedHashMap实现, 与@{@link LRUWithHashMap}实现类似.
 * 关键点: 1. 将accessOrder设置为true. 2.重写removeEldestEntry方法.
 *
 * LinkedHashMap内部实现使用双链表+HashMap:
 *  1. 当HashMap每放入一个Entry的时候, 在{@link java.util.HashMap#newNode(int, Object, Object, HashMap.Node)}
 * 会将Entry加到双链表的尾部, 然后在遍历的时候使用以双链表的顺序来遍历HashMap.
 *  2. 执行put操作的时候, 会在{@link java.util.HashMap#afterNodeInsertion(boolean)}里面会判断是否要移除元素.
 *  3. 执行get操作的时候, 会在{@link java.util.HashMap#afterNodeAccess(HashMap.Node)})}里面会将
 * 访问的元素放到链表的末尾.
 *
 * @see LRUWithHashMap
 */
public class LRUWithLinkedHashMap {

    class Solution extends LinkedHashMap<Integer, Integer> {
        private int capacity;

        public Solution(int capacity) {
            super(capacity, 0.75f, true);
            this.capacity = capacity;
        }

        public int get(int key) {
            return getOrDefault(key, -1);
        }

        public void put(int key, int value) {
            super.put(key, value);
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
            return size() > capacity;
        }
    }
}
