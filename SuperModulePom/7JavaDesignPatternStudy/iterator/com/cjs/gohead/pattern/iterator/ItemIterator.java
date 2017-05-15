package com.cjs.gohead.pattern.iterator;

/**
 *  ItemIterator interface.
 *
 * @see java.util.Iterator
 * Created by chenjingshuai on 17-5-15.
 */
public interface ItemIterator {
    boolean hasNext();

    Item next();
}
