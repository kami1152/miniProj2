package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.mapper.CodeMapper;
import com.example.demo.service.UserService;
import com.example.demo.vo.UserRequestVO;
import com.example.demo.vo.UserResponseVO;
import com.example.demo.vo.UserVO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

   

    @RequestMapping("/list")
    public String userList(Model model,@Valid UserRequestVO userRequestVO, BindingResult bindingResult) {
        
        if(bindingResult.hasErrors()){
            userRequestVO = UserRequestVO.builder().build();
        }
       
        model.addAttribute("userResponseVO", userService.getList(userRequestVO));
        System.out.println(userRequestVO);
        
        List<String> boxlist = new ArrayList<>();
        boxlist.add("아이디");
        boxlist.add("성별");
        boxlist.add("전화번호");
        boxlist.add("권한");
        boxlist.add("잠금여부");
        model.addAttribute("boxlist", boxlist);
        return "admin/list";
    }

    @RequestMapping("/jsonBoardInfo")
    @ResponseBody
    public Map<String, Object> jsonBoardInfo(@RequestBody UserVO user) throws Exception{
        Map<String, Object> map = new HashMap<>();
        UserVO resultVO = userService.getView(user.getMember_id());
        System.out.println("json : " + resultVO);
        if(resultVO != null){
            map.put("status", 0);
            map.put("jsonBoard", resultVO);
        }else{
            map.put("status",-99);
            map.put("statusMessage","no info");
        }
        return map;
    }

    @RequestMapping("/deleteAdmin")
    public String deleteAdmin(UserVO user){
        System.out.println("delete start");
        System.out.println(user);

        int n = userService.deleteAdmin(user);
        if(n>0){
            System.out.println("success");
        }
        return "redirect:/admin/list";

    }



    @RequestMapping("/unlockService")
    @ResponseBody
    public Map<String, Object> unlockService(@RequestBody Map<String, List<String>> requestBody) throws Exception{
        Map<String, Object> response = new HashMap<>();
        List<String> memberIds = requestBody.get("memberIds");
        System.out.println(memberIds);
        for(String name : memberIds){
            userService.adminUnlockUserLogin(name);
        }

        response.put("status", 0);
        response.put("msg", "Accounts unlocked successfully.");
        return response;
    }

}
