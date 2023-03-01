package com.example.nettyrpcproto.cons;

import com.example.nettyrpcproto.core.RpcNettyFuture;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class RequestConstants {


    public static final AtomicLong REQUEST_ID  = new AtomicLong();


    public static final Map<Long, RpcNettyFuture> REQUEST_MAP = new ConcurrentHashMap<>();
}
