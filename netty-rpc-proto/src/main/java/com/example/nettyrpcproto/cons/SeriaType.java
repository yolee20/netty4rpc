package com.example.nettyrpcproto.cons;

public enum SeriaType {

    JAVA_SERIAL((byte) 1),
    JSON_SERIAL((byte) 2);

    private byte type;

    SeriaType(byte type) {
        this.type = type;
    }

    public byte type(){
        return this.type;
    }
}
