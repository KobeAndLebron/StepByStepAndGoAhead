package com.cjs.nio;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 数据零拷贝[操作系统要支持]: 在不需要修改数据的情况下, 直接将数据在内核完成拷贝, 不需要再拷贝到用户空间.
 * 减少了两次没必要的内核态/的切换和两次数据的拷贝.
 *
 * 零拷贝比普通模式的拷贝速度快三倍多.
 */
public class NioZeroCopy {
    public static void main(String[] args) {
        String currentDir = System.getProperty("user.dir");

        String inputFileName = currentDir + "/8JAVASourceCode/src/main/resources/nio.text";
        String outputFileName = inputFileName + "_out";

        File inputFile = new File(inputFileName);
        File outputFile = new File(outputFileName);
        try (
            FileChannel fileChannel = new RandomAccessFile(inputFile, "rw").getChannel();
            FileChannel outputChannel = new RandomAccessFile(outputFile, "rw").getChannel();) {

            fileChannel.transferTo(0, fileChannel.size(), outputChannel);
            // fileChannel.transferFrom(xxx);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
