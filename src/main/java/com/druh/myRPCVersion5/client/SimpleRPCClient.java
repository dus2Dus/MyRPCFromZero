package com.druh.myRPCVersion5.client;

import com.druh.myRPCVersion5.common.RPCRequest;
import com.druh.myRPCVersion5.common.RPCResponse;
import com.druh.myRPCVersion5.register.ServiceRegister;
import com.druh.myRPCVersion5.register.ZkServiceRegister;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/*
    在verison3中，RPCClient接口的实现类xxxRPCClient，其实相当于是version2中的IOClient
    为什么要这么做？因为普通方式和netty方式的IOClient实现方式是不同的，所以直接这样搞两个RPCClient，代表不同的建立连接和发送请求的方式
 */
@AllArgsConstructor
public class SimpleRPCClient implements RPCClient {
    private String host;
    private int port;
    private ServiceRegister serviceRegister;

    public SimpleRPCClient() {
        // 初始化注册中心，建立连接
        this.serviceRegister = new ZkServiceRegister();
    }

    // 客户端发起一次请求调用，Socket建立连接，发起请求Request，得到响应Response
    // 这里的request是封装好的，不同的service需要进行不同的封装， 客户端只知道Service接口，需要一层动态代理根据反射封装不同的Service
    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        // 从注册中心获取host，port
        InetSocketAddress address = serviceRegister.serviceDiscovery(request.getInterfaceName());
        host = address.getHostName();
        port = address.getPort();

        try {
            // 发起一次Socket连接请求
            Socket socket = new Socket(host, port);

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println(request);
            objectOutputStream.writeObject(request);
            objectOutputStream.flush();

            RPCResponse response = (RPCResponse) objectInputStream.readObject();

            //System.out.println(response.getData());
            return response;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println();
            return null;
        }
    }
}
