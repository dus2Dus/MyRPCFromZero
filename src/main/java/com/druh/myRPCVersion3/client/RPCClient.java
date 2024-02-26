package com.druh.myRPCVersion3.client;

import com.druh.myRPCVersion3.common.RPCRequest;
import com.druh.myRPCVersion3.common.RPCResponse;

/*
    假如我们现在已经有了两个客户端：SimpleRPCClient(使用java BIO的方式)， NettyRPCClient（使用netty进行网络传输）
    那么它们两者的共性是啥？发送请求与得到response是共性， 而建立连接与发送请求的方式是不同点。
 */
public interface RPCClient {
    // 抽象出他们的共性
    RPCResponse sendRequest(RPCRequest request);
}
