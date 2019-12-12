package com.cjs.socket.bio.single_thread;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class BIOSingleThreadClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 2000);


            // Thread.sleep(1000000);
            // socket.getOutputStream().write("向服务器发送数据".getBytes());

            Scanner sc = new Scanner(System.in);
            String message = sc.next();
            socket.getOutputStream().write(message.getBytes());
            socket.close();
            sc.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
