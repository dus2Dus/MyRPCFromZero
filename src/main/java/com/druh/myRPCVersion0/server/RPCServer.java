package com.druh.myRPCVersion0.server;

import com.druh.myRPCVersion0.common.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer {
    public static void main(String[] args) {
        UserSerivceImpl userSerivce = new UserSerivceImpl();
        try {
            ServerSocket serverSocket = new ServerSocket(8899);
            System.out.println("服务端启动了");

            // BIO的方式监听Socket
            while (true) {
                Socket socket = serverSocket.accept();
                // 开启一个线程去处理
                new Thread(() -> {
                    try {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                        // 读取客户端传过来的id
                        int id = objectInputStream.readInt();
                        User user = userSerivce.getUserByUserId(id);
                        // 写入User对象给客户端
                        objectOutputStream.writeObject(user);
                        objectOutputStream.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.out.println("从IO中读取数据错误");
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }
    }
}
