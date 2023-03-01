package com.example.nettyrpcproto.serial;

import com.example.nettyrpcproto.cons.SeriaType;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class SerializerManager {


    public static final ConcurrentHashMap<Byte, ISerializer> serilzersMaps = new ConcurrentHashMap();

    static {
        JavaSerializer javaSerializer = new JavaSerializer();
        JsonSerializer jsonSerializer = new JsonSerializer();
        serilzersMaps.put(SeriaType.JAVA_SERIAL.type(),javaSerializer);
        serilzersMaps.put(SeriaType.JSON_SERIAL.type(), jsonSerializer);
    }

    public static ISerializer getSerializer(byte type){
        ISerializer serializer = serilzersMaps.get(type);
        if (Objects.isNull(serializer)){
            return new JavaSerializer();
        }
        return serializer;
    }



}
