package com.druh.myRPCVersion3.Server;

import com.druh.myRPCVersion3.service.BlogService;
import com.druh.myRPCVersion3.service.BlogServiceImpl;
import com.druh.myRPCVersion3.service.UserSerivceImpl;
import com.druh.myRPCVersion3.service.UserService;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserSerivceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        NettyRPCServer nettyRPCServer = new NettyRPCServer(serviceProvider);
        nettyRPCServer.start(8899);
    }
}
