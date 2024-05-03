package com.example.demo.config;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.handler.AuthFailureHandler;
import com.example.demo.handler.AuthSuccessHandler;
import com.example.demo.service.UserService;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity // <- 필터 어노테이션
//@EnableMethodSecurity
//@EnableGlobalAuthentication
public class SecurityConfig {

    @Autowired
    private UserService userService;
    @Autowired
    private AuthSuccessHandler authSuccessHandler;
    @Autowired
    private AuthFailureHandler authFailureHandler;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
                .dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
                .requestMatchers("/css/**", "/js/**","/**","/user/**").permitAll()
                .anyRequest().authenticated()) 
            .formLogin(login -> login
                .usernameParameter("email")
                .passwordParameter("password")
                .loginPage("/user/loginForm")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                .successHandler(authSuccessHandler)
                .failureHandler(authFailureHandler)
                .permitAll())
            .logout(logout->logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
                .logoutSuccessUrl("/login/loginForm")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll())
            .sessionManagement(session->{
                try {
                    session
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(false)
                    .expiredUrl("/login/loginForm?error=true&exception=" + URLEncoder.encode("세션이 만료되었습니다. 다시 로그인 해주세요", "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            })
            ;
            
        return http.build();
    }



}
