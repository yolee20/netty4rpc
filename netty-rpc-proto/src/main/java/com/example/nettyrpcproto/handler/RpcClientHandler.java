package com.example.nettyrpcproto.handler;

import com.example.nettyrpcproto.cons.RequestConstants;
import com.example.nettyrpcproto.core.RpcNettyFuture;
import com.example.nettyrpcproto.core.RpcProtocol;
import com.example.nettyrpcproto.core.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcClientHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {
    @Override
    protected void channelRead0(ChannelHandlerContext context,
                                RpcProtocol<RpcResponse> msg) throws Exception {


        log.info("receive Rpc Server Result 【{}】",msg);

        long requestId = msg.getHeader().getRequestId();
        RpcNettyFuture nettyFuture = RequestConstants.REQUEST_MAP.remove(requestId);
        nettyFuture.getPromise().setSuccess(msg.getContent());//返回结果


    }
}
