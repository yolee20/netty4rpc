package com.example.nettyrpcconsumer.proxy;

import java.lang.reflect.Proxy;

public class RpcClientProxy <T> {

    public <T> T clientProxy(final Class<T> interfaceCls,String host,int port){

        return (T) Proxy.newProxyInstance(interfaceCls.getClassLoader(),
                new Class<?>[]{interfaceCls},new RpcInvokerProxy(host, port));
    }









}
