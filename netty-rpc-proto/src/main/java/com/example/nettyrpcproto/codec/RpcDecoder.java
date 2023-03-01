package com.example.nettyrpcproto.codec;

import com.example.nettyrpcproto.cons.ReqType;
import com.example.nettyrpcproto.cons.RpcConstants;
import com.example.nettyrpcproto.core.Header;
import com.example.nettyrpcproto.core.RpcProtocol;
import com.example.nettyrpcproto.core.RpcRequest;
import com.example.nettyrpcproto.core.RpcResponse;
import com.example.nettyrpcproto.serial.ISerializer;
import com.example.nettyrpcproto.serial.SerializerManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * 解码器
 */
@Slf4j
public class RpcDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext context,
                          ByteBuf byteBuf, List<Object> list) throws Exception {

        log.info("rpc开始解码");
        if(byteBuf.readableBytes()< RpcConstants.HEAD_LENGTH){
            return;
        }
        short magic = byteBuf.readShort();//读取2个字节magic
        if (magic!=RpcConstants.MAGIC){
            throw new IllegalArgumentException("Illegal request parameter 'magic':"+magic);
        }

        byteBuf.markReaderIndex();//标记开始读取索引

        byte serialType = byteBuf.readByte();//1个字节序列化类型
        byte reqType = byteBuf.readByte();//1个字节消息类型
        long requestId = byteBuf.readLong();//8
        int dataLength = byteBuf.readInt();//4

        //校验数据是否完整
        if(byteBuf.readableBytes()<dataLength){
            byteBuf.resetReaderIndex();//重置本次读取索引  本次不读取
            return;
        }

        byte[] content = new byte[dataLength];
        byteBuf.readBytes(content);//读取到content中

        Header header = new Header(magic, serialType, reqType, requestId, dataLength);
        ISerializer serializer = SerializerManager.getSerializer(serialType);

        ReqType rt = ReqType.findByCode(reqType);
        switch (rt){
            case REQUEST:
                RpcRequest rpcRequest = (RpcRequest) serializer.deserialize(content, RpcRequest.class);
                RpcProtocol<RpcRequest> rpcProtocol = new RpcProtocol<>();
                rpcProtocol.setHeader(header);
                rpcProtocol.setContent(rpcRequest);
                list.add(rpcProtocol);
                break;
            case RESPONSE:
                RpcResponse rpcResponse = (RpcResponse) serializer.deserialize(content, RpcResponse.class);
                RpcProtocol<RpcResponse> rpcProtocol1 = new RpcProtocol<>();
                rpcProtocol1.setHeader(header);
                rpcProtocol1.setContent(rpcResponse);
                list.add(rpcProtocol1);
                break;
            case HEARTBEAT:
                //TODO
                break;
            default:
                break;
        }



    }
}
