package com.druh.myRPCVersion6.Server;

import com.druh.myRPCVersion6.service.BlogService;
import com.druh.myRPCVersion6.service.BlogServiceImpl;
import com.druh.myRPCVersion6.service.UserSerivceImpl;
import com.druh.myRPCVersion6.service.UserService;

public class TestServer2 {
    public static void main(String[] args) {
        UserService userService = new UserSerivceImpl();
        BlogService blogService = new BlogServiceImpl();

        ServiceProvider serviceProvider = new ServiceProvider("127.0.0.1", 8900);
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer RPCServer = new NettyRPCServer(serviceProvider);
        RPCServer.start(8900);
    }
}
