package com.example.nettyrpcconsumer;

import com.example.nettyrpcapi.service.IUserService;
import com.example.nettyrpcconsumer.proxy.RpcClientProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.nio.charset.StandardCharsets;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.nettyrpcproto.*")
public class NettyRpcConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyRpcConsumerApplication.class, args);


        String host = "127.0.0.1";
        int port = 8080;


        IUserService userService = new RpcClientProxy<IUserService>().clientProxy(IUserService.class, host, port);
        String result = userService.saveUser("yolee");

        System.out.println("rpc -consumer exe saveUser() result = "+result);



    }

}
