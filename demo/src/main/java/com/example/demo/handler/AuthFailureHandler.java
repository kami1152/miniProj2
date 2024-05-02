package com.example.demo.handler;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.example.demo.data.GlobalData;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
		
	    String msg = "Invalid Email or Password";

		System.out.println(exception);
		System.out.println(request.getParameter("email"));
		System.out.println(GlobalData.globalCount);

		// exception 관련 메세지 처리
	    if (exception instanceof LockedException) {
        	msg = "계정이 잠겼습니다";
	    } else if (exception instanceof DisabledException) {
        	msg = "DisabledException account";
        } else if(exception instanceof CredentialsExpiredException) {
        	msg = "CredentialsExpiredException account";
        } else if(exception instanceof BadCredentialsException ) {
        	msg = "아이디 또는 비밀번호가 잘못되었습니다";
        }
		
		setDefaultFailureUrl("/user/loginForm?error=true&exception=" +URLEncoder.encode(msg, "utf-8"));
		
	   
		
	    
	
	    super.onAuthenticationFailure(request, response, exception);
	}
}