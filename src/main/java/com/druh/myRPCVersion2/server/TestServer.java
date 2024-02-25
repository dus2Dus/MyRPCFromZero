package com.druh.myRPCVersion2.server;

public class TestServer {
    public static void main(String[] args) {
        UserSerivceImpl userSerivce = new UserSerivceImpl();
        BlogServiceImpl blogService = new BlogServiceImpl();

//        HashMap<String, Object> serviceProvide = new HashMap<>();
//        // 暴露两个服务接口， 即在RPCServer中加一个HashMap
//        serviceProvide.put("com.druh.myRPCVersion2.service.UserService", userSerivce);
//        serviceProvide.put("com.druh.myRPCVersion2.service.BlogService", blogService);

        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userSerivce);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer RPCServer = new SimpleRPCServer(serviceProvider);
        RPCServer.start(8899);
    }
}
