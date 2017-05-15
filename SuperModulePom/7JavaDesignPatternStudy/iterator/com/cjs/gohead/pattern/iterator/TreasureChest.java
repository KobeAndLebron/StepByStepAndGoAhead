package com.cjs.gohead.pattern.iterator;

import java.util.*;

/**
 *  TreasureChest, the collection class.
 *
 *  ArrayList's DOC:<br/>
 *  <strong>Note that this implementation is not synchronized.</strong>
 *  If multiple threads access an <tt>ArrayList</tt> instance concurrently,
 * and at least one of the threads modifies the list structurally, it
 * <i>must</i> be synchronized externally.  (A structural modification is
 * any operation that adds or deletes one or more elements, or explicitly
 * resizes the backing array; merely setting the value of an element is not
 * a structural modification.)  This is typically accomplished by
 * synchronizing on some object(represented by TreasureChest in this example)
 * that naturally encapsulates the list.
 *
 *
 * Created by chenjingshuai on 17-5-15.
 */
public class TreasureChest {
    private List<Item> items;
    private int modCount;

    public TreasureChest() {
        items = new LinkedList<>();
        items.add(new Item("Kobe"));
        items.add(new Item("Jordan"));
        items.add(new Item("Paul"));
        items.add(new Item("Russell"));
        modCount++;
    }

    public synchronized void add(Item item) {
        items.add(item);
        modCount++;
    }

    public ItemIterator iterator() {
        return new TreasureChestIterator();
    }

    private class TreasureChestIterator implements ItemIterator {
        private int index = 0;

        private int expectedModCount = modCount;

        @Override
        public boolean hasNext() {
            return index < items.size();
        }

        @Override
        public Item next() {
            checkForComodification();
            if (index >= items.size()) {
                throw new NoSuchElementException();
            }
            return items.get(index++);
        }

        private void checkForComodification() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

}
