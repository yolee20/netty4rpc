package com.example.nettyrpcproto.core;

import io.netty.util.concurrent.Promise;
import lombok.Data;

@Data
public class RpcNettyFuture <T>{


    private Promise<T> promise;

    public RpcNettyFuture(Promise<T> promise) {
        this.promise = promise;
    }
}
