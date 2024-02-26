package com.druh.myRPCVersion3.Server;

// 把RPCServer抽象成接口，以后的服务端实现这个接口即可
public interface RPCServer {
    void start(int port);
    void stop();
}
