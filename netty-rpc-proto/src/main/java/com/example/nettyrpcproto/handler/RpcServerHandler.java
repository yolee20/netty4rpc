package com.example.nettyrpcproto.handler;

import com.example.nettyrpcproto.cons.ReqType;
import com.example.nettyrpcproto.core.Header;
import com.example.nettyrpcproto.core.RpcProtocol;
import com.example.nettyrpcproto.core.RpcRequest;
import com.example.nettyrpcproto.core.RpcResponse;
import com.example.nettyrpcproto.spring.SpringBeanManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Slf4j
public class RpcServerHandler extends SimpleChannelInboundHandler <RpcProtocol<RpcRequest>>{


    @Autowired
    ApplicationContext context;
    @Override
    protected void channelRead0(ChannelHandlerContext context,
                                RpcProtocol<RpcRequest> rpcRequestRpcProtocol) throws Exception {



        log.info("begin rpc server handler");
        Header header = rpcRequestRpcProtocol.getHeader();
        header.setReqType(ReqType.RESPONSE.type());

        Object result = invoke(rpcRequestRpcProtocol.getContent());
        RpcProtocol<RpcResponse> rpcResponse = new RpcProtocol<>();
        RpcResponse response = new RpcResponse();
        response.setCode(200);
        response.setMsg("success");
        response.setData(result);

        log.info("rpc server handler data = 【{}】 ",result);
        rpcResponse.setHeader(header);
        rpcResponse.setContent(response);

        log.info("end rpc server handler ");
        context.writeAndFlush(rpcResponse);




    }







    private Object invoke(RpcRequest rpcRequest){
        try {

            Class<?> clazz = Class.forName(rpcRequest.getClssName());
            Object bean = SpringBeanManager.getBean(clazz);
            //Object bean = SpringUtils.getBean(clazz);
            Method method = clazz.getDeclaredMethod(rpcRequest.getInvokeName(), rpcRequest.getParamTypes());
            return method.invoke(bean,rpcRequest.getParams());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

    }


}
