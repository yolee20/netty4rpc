package com.example.nettyrpcconsumer.proxy;

import com.example.nettyrpcproto.cons.ReqType;
import com.example.nettyrpcproto.cons.RequestConstants;
import com.example.nettyrpcproto.cons.RpcConstants;
import com.example.nettyrpcproto.cons.SeriaType;
import com.example.nettyrpcproto.core.*;
import com.example.nettyrpcproto.protocol.NettyClient;
import io.netty.channel.DefaultEventLoop;
import io.netty.util.concurrent.DefaultPromise;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
@Slf4j
public class RpcInvokerProxy implements InvocationHandler {

    private String host;

    private int port;

    private Object target;

    public RpcInvokerProxy(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        log.info("RpcInvokerProxy - begin invoke ...");

        long requestId = RequestConstants.REQUEST_ID.incrementAndGet();

        Header header = new Header(RpcConstants.MAGIC,
                SeriaType.JSON_SERIAL.type(),//java-
                ReqType.REQUEST.type(),
                requestId,0
                );


        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClssName(method.getDeclaringClass().getName());//class
        rpcRequest.setInvokeName(method.getName());//方法名
        rpcRequest.setParams(args);//参数
        rpcRequest.setParamTypes(method.getParameterTypes());//参数类型

        RpcProtocol<RpcRequest> rpcProtocol = new RpcProtocol<>();
        rpcProtocol.setHeader(header);
        rpcProtocol.setContent(rpcRequest);


        //构建client
        NettyClient nettyClient = new NettyClient(this.host,this.port);
        nettyClient.sendRequest(rpcProtocol);

        RpcNettyFuture<RpcResponse> futurePromise = new RpcNettyFuture<>(new DefaultPromise<RpcResponse>(new DefaultEventLoop()));

        //保存sessionid-future关系
        RequestConstants.REQUEST_MAP.put(requestId,futurePromise);



        log.info("RpcInvokerProxy - invoke done request =【{}】",requestId);

        //get 是阻塞方法
        return futurePromise.getPromise().get().getData();
    }
}
