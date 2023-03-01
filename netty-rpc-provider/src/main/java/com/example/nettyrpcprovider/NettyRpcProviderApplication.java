package com.example.nettyrpcprovider;

import com.example.nettyrpcproto.protocol.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.nettyrpcproto.spring","com.example.nettyrpcprovider.service"})
public class NettyRpcProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyRpcProviderApplication.class, args);
        new NettyServer("127.0.0.1",8080).startNettyServer();
    }

}
