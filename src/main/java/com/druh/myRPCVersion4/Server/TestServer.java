package com.druh.myRPCVersion4.Server;

import com.druh.myRPCVersion4.service.BlogService;
import com.druh.myRPCVersion4.service.BlogServiceImpl;
import com.druh.myRPCVersion4.service.UserSerivceImpl;
import com.druh.myRPCVersion4.service.UserService;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserSerivceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer RPCServer = new NettyRPCServer(serviceProvider);
        RPCServer.start(8899);
    }
}
