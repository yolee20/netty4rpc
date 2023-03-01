package com.example.nettyrpcproto.protocol;

import com.example.nettyrpcproto.core.RpcProtocol;
import com.example.nettyrpcproto.core.RpcRequest;
import com.example.nettyrpcproto.handler.RpcClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyClient {

    private final Bootstrap bootstrap;
    private final NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

    private String serverAddress;
    private int serverPort;

    public NettyClient(String serverAddress, int serverPort) {
        log.info("begin init 【Netty Clinet {} ,{}】 ",serverAddress,serverPort);
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;


        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new RpcClientInitializer());

    }


    public void sendRequest(RpcProtocol<RpcRequest> rpcProtocol) throws InterruptedException {

        ChannelFuture future = bootstrap.connect(this.serverAddress, this.serverPort).sync();
        future.addListener(listener->{
            if (listener.isSuccess()) {
                log.info("connect rpc server 【{}】 success ",this.serverAddress);
            }else {
                log.info("connect rpc server 【{}】 failed ",this.serverAddress);
                future.cause().printStackTrace();
                eventLoopGroup.shutdownGracefully();
            }
        });

        future.channel().writeAndFlush(rpcProtocol);
        log.info("netty client request success from server 【{}】",this.serverAddress);

    }
}
