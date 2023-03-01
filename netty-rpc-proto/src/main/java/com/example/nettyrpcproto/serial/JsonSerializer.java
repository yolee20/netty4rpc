package com.example.nettyrpcproto.serial;

import cn.hutool.core.bean.BeanUtil;


import com.alibaba.fastjson2.JSON;
import com.example.nettyrpcproto.cons.SeriaType;

public class JsonSerializer implements ISerializer{
    @Override
    public byte[] serialize(Object obj) {
        return JSON.toJSONString(obj).getBytes();
    }

    @Override
    public Object deserialize(byte[] data, Class clazz) {
        //return  BeanUtil.copyProperties(new String(data),clazz);
       return JSON.parseObject(new String(data),clazz);//
    }

    @Override
    public byte type() {
        return SeriaType.JSON_SERIAL.type();
    }
}
