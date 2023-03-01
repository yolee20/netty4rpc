package com.example.nettyrpcproto.protocol;

import com.example.nettyrpcproto.handler.RpcServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NettyServer {


    private String serverAddress;

    private int port;

    public NettyServer(String serverAddress, int port) {
        this.serverAddress = serverAddress;
        this.port = port;
    }


    public void startNettyServer(){

        log.info("=========== starting netty server ==========");
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss,worker)
                .channel(NioServerSocketChannel.class)
                .childHandler(new RpcServerInitializer());//自定义 rpchandler


        try {
            ChannelFuture future = serverBootstrap.bind(this.serverAddress,this.port).sync();

            log.info("=========== netty server start success on port {} ==========",this.port);
            future.channel().closeFuture().sync();//同步关闭

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }


    }
}
