package com.druh.myRPCVersion6.client;

import com.druh.myRPCVersion6.common.RPCResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

/*
    NettyClientHandler用于读取服务端发送过来的RPCResponse消息对象，并将RPCResponse消息对象保存到AttributeMap上，AttributeMap可以看作是一个Channel的共享数据源
    这样的话，我们就能通过Channel和key将数据读取出来
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<RPCResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RPCResponse rpcResponse) throws Exception {
        // 接收到response, 给channel设计别名，让sendRequest里读取response
        AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
        // 将服务端的返回结果保存到AttributeMap上，AttributeMap可以看作是一个Channel的共享数据源
        channelHandlerContext.attr(key).set(rpcResponse);
        channelHandlerContext.channel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
