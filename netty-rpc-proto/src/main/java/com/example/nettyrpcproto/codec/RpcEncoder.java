package com.example.nettyrpcproto.codec;

import com.example.nettyrpcproto.core.Header;
import com.example.nettyrpcproto.core.RpcProtocol;
import com.example.nettyrpcproto.serial.ISerializer;
import com.example.nettyrpcproto.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcEncoder extends MessageToByteEncoder<RpcProtocol<Object>> {
    @Override
    protected void encode(ChannelHandlerContext context,
                          RpcProtocol<Object> msg, ByteBuf out) throws Exception {

        log.info("rpc开始编码");
        Header header = msg.getHeader();

        out.writeShort(header.getMagic());//2
        byte serialType = header.getSerialType();
        out.writeByte(serialType);//1
        out.writeByte(header.getReqType());//1
        out.writeLong(header.getRequestId());//8


        ISerializer serializer = SerializerManager.getSerializer(serialType);
        byte[] data = serializer.serialize(msg.getContent());
        header.setLength(data.length);

        out.writeInt(data.length);//4
        out.writeBytes(data);
        log.info("rpc编码完成 = "+data);


    }
}
