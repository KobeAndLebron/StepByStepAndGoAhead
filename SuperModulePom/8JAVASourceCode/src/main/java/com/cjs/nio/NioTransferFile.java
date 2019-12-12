package com.cjs.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 非阻塞IO读文件: 通过Channel+Buffer将一个文件的内容读取到另一个文件.
 */
public class NioTransferFile {
    public static void main(String[] args) throws IOException {
        String currentDir = System.getProperty("user.dir");

        String inputFile = currentDir + "/8JAVASourceCode/src/main/resources/nio.text";
        String outputFile = inputFile + "_out";

        // Harvest try-with-resources语法: 声明了一个或多个资源(实现了Closeable或AutoCloseable接口的对象, 使用后需关闭).
        FileInputStream fileInputStreamCopy = null;

        try (FileInputStream fileInputStream = new FileInputStream(inputFile);
             FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
             FileChannel inputChannel = fileInputStream.getChannel();
             FileChannel outputChannel = fileOutputStream.getChannel();) {

            fileInputStreamCopy = fileInputStream;

            ByteBuffer byteBuffer = ByteBuffer.allocate(13);
            while (true) {
                // Harvest 如果不使用clear方法, 那么再第二次循环到来, position==limit, 此时Buffer已满,
                // 再也写不进去数据, 会导致每次read的返回值为0, 陷入死循环.
                byteBuffer.clear();
                int read = inputChannel.read(byteBuffer);
                if (read == -1) { // 文件已读完.
                    ByteBuffer tempByteBuffer = ByteBuffer.allocate(100);
                    tempByteBuffer.put("程序已经读完, 利用.".getBytes());
                    tempByteBuffer.flip();
                    inputChannel.write(tempByteBuffer);
                    break;
                }
                byteBuffer.flip();

                while (byteBuffer.hasRemaining()) {
                    System.out.println((char) byteBuffer.get());
                }

                // 回放功能.
                // 将Position置为0, Limit和Capacity不变, 即再读一边Buffer里面的数据.
                byteBuffer.rewind();

                outputChannel.write(byteBuffer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 抛出"Stream Closed"的异常, 证明Try-with-resources的作用.
        System.out.println("FileInputStream available?" + fileInputStreamCopy.available());

    }
}
