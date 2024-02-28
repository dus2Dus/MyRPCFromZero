package com.druh.myRPCVersion5.Server;

import com.druh.myRPCVersion5.service.BlogService;
import com.druh.myRPCVersion5.service.BlogServiceImpl;
import com.druh.myRPCVersion5.service.UserSerivceImpl;
import com.druh.myRPCVersion5.service.UserService;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserSerivceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider("127.0.0.1", 8899);
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer RPCServer = new NettyRPCServer(serviceProvider);
        RPCServer.start(8899);
    }
}
