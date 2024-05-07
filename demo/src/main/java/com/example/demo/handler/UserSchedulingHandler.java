package com.example.demo.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserSchedulingHandler {
    
    private final UserService userService;

    @Scheduled(fixedDelay = 600000)
    public void unlockUserLogin(){
        System.out.println("user login unlock process start");
        userService.unlockUserLogin();
    }

}
