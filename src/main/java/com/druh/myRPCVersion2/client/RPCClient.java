package com.druh.myRPCVersion2.client;

import com.druh.myRPCVersion2.common.User;
import com.druh.myRPCVersion2.service.UserService;
import com.druh.myRPCVersion2.common.Blog;
import com.druh.myRPCVersion2.service.BlogService;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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

        // 客户断中添加新的测试用例
        BlogService blogService = clientProxy.getProxy(BlogService.class);
        Blog blogById = blogService.getBlogById(10000);
        System.out.println("从服务端得到的blog为：" + blogById);
    }
}
