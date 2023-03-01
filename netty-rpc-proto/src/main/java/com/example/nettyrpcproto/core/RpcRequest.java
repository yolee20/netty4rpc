package com.example.nettyrpcproto.core;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcRequest implements Serializable {

    private String clssName;
    private String invokeName;
    private Object[] params;
    private Class<?> [] paramTypes;

}
