package com.cjs.socket.bio.multi_thread;

import java.io.IOException;
import java.net.Socket;

public class BIOMultiThreadClient {
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Runnable runalbe = () -> {
                Socket socket;
                try {
                    socket = new Socket("localhost", 2000);

//                            Scanner sc = new Scanner(System.in);
//                            String message = sc.next();
                    try {
                        Thread.sleep(10000);
                        socket.getOutputStream().write("向服务器发送数据".getBytes());
//                                socket.getOutputStream().write(message.getBytes());
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            Thread thread = new Thread(runalbe);
            thread.start();
        }

    }
}
