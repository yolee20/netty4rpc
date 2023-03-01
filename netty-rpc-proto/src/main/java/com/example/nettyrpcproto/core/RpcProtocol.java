package com.example.nettyrpcproto.core;

import lombok.Data;

import java.io.Serializable;

@Data
public class RpcProtocol <T> implements Serializable {

    private Header header;
    private T content;




}
