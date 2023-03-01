package com.example.nettyrpcprovider.service;

import com.example.nettyrpcapi.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class IUserServiceImpl implements IUserService {


    @Override
    public String saveUser(String name) {
        log.info("********** IUserServiceImpl begin saveUser {}",name);
        return "save user success :"+name;
    }
}
