package com.druh.myRPCVersion6.register;

import java.net.InetSocketAddress;

// 服务注册接口，两大基本功能:
// 注册：保存服务与地址。 查询：根据服务名查找地址
public interface ServiceRegister {
    // 注册
    void register(String serviceName, InetSocketAddress serverAddress);
    // 发现
    InetSocketAddress serviceDiscovery(String serviceName);
}