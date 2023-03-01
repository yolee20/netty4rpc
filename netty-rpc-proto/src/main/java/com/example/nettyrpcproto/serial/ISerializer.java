package com.example.nettyrpcproto.serial;

public interface ISerializer<T> {

     <T> byte[] serialize(T obj);

     <T> T deserialize(byte [] data,Class <T> clazz);

     byte type();


}
