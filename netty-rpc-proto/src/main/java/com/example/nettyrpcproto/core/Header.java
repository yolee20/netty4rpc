package com.example.nettyrpcproto.core;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@AllArgsConstructor
@Data
public class Header implements Serializable {
    private short magic;//魔数 2字节
    private byte serialType;// 序列化类型 1字节
    private byte reqType;//消息类型 1字节
    private long requestId;//请求id 8字节
    private int length;//消息体长度 4字节

}
