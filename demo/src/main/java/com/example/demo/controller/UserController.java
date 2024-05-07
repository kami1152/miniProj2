package com.example.demo.controller;


import java.util.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.service.UserService;
import com.example.demo.vo.UserHobbyVO;
import com.example.demo.vo.UserVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping("/loginForm")
    public String loginFormview(Model model) {
        return "user/loginForm";
    }

    @RequestMapping("/registerForm")
    public String registerFrom(Model model) {
        
        List<UserHobbyVO> hblist = userService.gethobbyList();
        List<String> list =  new ArrayList<>();
        for(UserHobbyVO item : hblist){
            list.add(item.getHobbyname());
        }
        //System.out.println(hblist);
        model.addAttribute("hblist", list);
        return "user/registerForm";
    }


    @RequestMapping("/register")
    public String register(Model model,UserVO userVO, UserHobbyVO userHobbyVO){
        System.out.println(userHobbyVO);
        System.out.println(userVO);
        int n = userService.register(userVO,userHobbyVO);
        System.out.println("insert : " + n);
        return "user/login";
    }


    @RequestMapping("/checkID")
    @ResponseBody
    public Map<String, Object> idCheckfunction(
        @RequestBody Map<String, Object> requsetBody
    ) {
        Map<String, Object> response = new HashMap<>();
        Object memberid = requsetBody.get("member_id");
        boolean result = userService.checkID(memberid.toString());
        if(!result){
            response.put("status",1);
            response.put("msg","이미 존재하는 아이디");
        }else{
            response.put("status", 0);
            response.put("msg","사용 가능한 아이디");
        }
        
        return response;
    }

    

    // @GetMapping("/loginForm")
    // public void getLoginview(Model model, @RequestParam(value = "error", required
    // = false)String error,
    // @RequestParam(value = "exception", required = false) String exception ){
    // model.addAttribute("error", error);
    // model.addAttribute("exception", exception);
    // }

}
