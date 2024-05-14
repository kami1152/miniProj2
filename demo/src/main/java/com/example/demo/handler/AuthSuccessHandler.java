package com.example.demo.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.mapper.UserMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication // 로그인한 사용자 정보가 있는
                                                                                                    // 객체
    ) throws IOException, ServletException {

       
        userMapper.updateMemberLastLogin(authentication.getName());
        userMapper.loginCountClear(authentication.getName());

        System.out.println("authentication ->" + authentication);

        //테스트
        // 성공시 이동할 주소
        // 설정(onfig)에서 defaultSuccessUrl("/") 으로 설정한 것 보다 아래의 코드로 설정한 것이 변경되서 동작함
        setDefaultTargetUrl("/board/list");

        super.onAuthenticationSuccess(request, response, authentication);
    }

}
