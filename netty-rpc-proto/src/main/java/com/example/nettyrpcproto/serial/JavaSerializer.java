package com.example.nettyrpcproto.serial;

import com.example.nettyrpcproto.cons.SeriaType;

import java.io.*;

public class JavaSerializer implements ISerializer{
    @Override
    public byte[] serialize(Object obj) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(bos);
            outputStream.writeObject(obj);
            bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new byte[0];
    }


    @Override
    public Object deserialize(byte[] data, Class clazz) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(data));
            return inputStream.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public byte type() {
        return SeriaType.JAVA_SERIAL.type();
    }
}
