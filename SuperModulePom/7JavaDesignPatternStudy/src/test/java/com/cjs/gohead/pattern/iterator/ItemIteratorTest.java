package com.cjs.gohead.pattern.iterator;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by chenjingshuai on 17-5-15.
 */
public class ItemIteratorTest {
    private final TreasureChest treasureChest = new TreasureChest();

    @Test
    public void testForAccuracyOfItemIterator() throws InterruptedException {
        ItemIterator iterator = treasureChest.iterator();
        for(; iterator.hasNext();) {
            Item item = iterator.next();
            Thread.sleep(10);
            System.out.println(item);
            assertNotNull(item);
        }
        try {
            iterator.next();
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            assertNotNull(e);
            return;
        }
        assertNotNull(null);
    }

    @Test
    public void testConCurrentModificationException() throws InterruptedException {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                treasureChest.add(new Item("Test for Concurrent modification."));
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
        try {
            testForAccuracyOfItemIterator();
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
            assertNotNull(e);
            return;
        }
        assertNotNull(null);
    }
}
