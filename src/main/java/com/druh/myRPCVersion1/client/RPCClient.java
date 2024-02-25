package com.druh.myRPCVersion1.client;

import com.druh.myRPCVersion1.common.User;
import com.druh.myRPCVersion1.service.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class RPCClient {
    public static void main(String[] args) {

        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8899);
        UserService proxy = clientProxy.getProxy(UserService.class);

        // 服务的方法1
        User userByUserId = proxy.getUserByUserId(10);
        System.out.println("从服务端得到的user为：" + userByUserId);

        // 服务的方法2
        User user = User.builder().userName("张三").id(100).sex(true).build();
        Integer i = proxy.insertUserId(user);
        System.out.println("向服务端插入数据：" + i);
    }
}
