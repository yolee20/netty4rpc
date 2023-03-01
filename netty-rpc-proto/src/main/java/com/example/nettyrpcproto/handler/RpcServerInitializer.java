package com.example.nettyrpcproto.handler;

import com.example.nettyrpcproto.codec.RpcDecoder;
import com.example.nettyrpcproto.codec.RpcEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class RpcServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {

        /**  12+4
         *     private short magic;//魔数 2字节
         *     private byte serialType;// 序列化类型 1字节
         *     private byte reqType;//消息类型 1字节
         *     private long requestId;//请求id 8字节
         *     private int length;//消息体长度 4字节
         */
        channel.pipeline()
                .addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                        12,
                        4,
                        0,
                        0))
                .addLast(new RpcDecoder()) //解码
                .addLast(new RpcEncoder()) //编码
                .addLast(new RpcServerHandler());// rpc服务端处理器



    }
}
