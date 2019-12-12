package com.cjs.nio;

import java.nio.ByteBuffer;

/**
 * SliceBuffer与原ByteBuffer共享相同的底层数组.
 */
public class SliceBuffer {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }

        buffer.position(2);
        buffer.limit(5);

        // 新的SliceBuffer的Position从0开始.
        ByteBuffer sliceBuffer = buffer.slice();
        // pos=0, limit=3, cap=3.
        System.out.println(sliceBuffer);
        for (int i = 0; i < sliceBuffer.capacity(); i++) {
            // Harvest Absolute operations take an explicit element index and do not
            // affect the position.
            byte b = sliceBuffer.get(i);
            b *= 2;
            sliceBuffer.put(i, b);
        }
        // pos=0, limit=3, cap=3.
        System.out.println(sliceBuffer);


        buffer.position(0);
        buffer.limit(buffer.capacity());
        // 等价于以下操作.
        // buffer.clear();
        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }

    }
}
