package com.example.nettyrpcproto.cons;

public enum ReqType {

    REQUEST((byte)1),//请求
    RESPONSE((byte)2),//响应
    HEARTBEAT((byte)3);//心跳


    private byte type;

    ReqType(byte type) {
        this.type = type;
    }

    public byte type(){
        return this.type;
    }


    public static ReqType findByCode(int code){
        for (ReqType reqType:ReqType.values()){
            if (reqType.type()==code){
                return reqType;
            }
        }

        return null;
    }
}
