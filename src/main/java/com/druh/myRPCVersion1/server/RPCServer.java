package com.druh.myRPCVersion1.server;

import com.druh.myRPCVersion1.common.RPCRequest;
import com.druh.myRPCVersion1.common.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
                // 开启一个线程去处理，这个类负责的功能太复杂，以后代码重构中，这部分功能要分离出来
                new Thread(() -> {
                    try {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                        // 读取客户端传过来的request
                        RPCRequest request = (RPCRequest) objectInputStream.readObject();
                        // 反射调用对应的方法
                        Method method = userSerivce.getClass().getMethod(request.getMethodName(), request.getParamsTypes());
                        // invoke是方法处理结果
                        Object invoke = method.invoke(userSerivce, request.getParams());
                        // 封装，写入response对象
                        objectOutputStream.writeObject(RPCResponse.success(invoke));
                        objectOutputStream.flush();
                    } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
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