package com.example.nettyrpcproto.core;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcResponse implements Serializable {

    private int code;
    private String msg;
    private Object data;

}
