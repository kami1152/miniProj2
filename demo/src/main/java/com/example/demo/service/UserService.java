package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.data.GlobalData;
import com.example.demo.mapper.UserMapper;
import com.example.demo.vo.UserHobbyVO;
import com.example.demo.vo.UserRequestVO;
import com.example.demo.vo.UserResponseVO;
import com.example.demo.vo.UserVO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class UserService implements UserDetailsService {


	@Autowired
	private UserMapper userMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public UserResponseVO<UserVO> getList(UserRequestVO userRequestVO) {
		
		List<UserVO> list = userMapper.getList(userRequestVO);
		UserResponseVO<UserVO> userResponseVO = UserResponseVO.<UserVO>withAll()
				.list(list)
				.build();

		return userResponseVO;
	}

	// 잠금해제
	public void unlockUserLogin() {
		List<UserVO> list = userMapper.LockUserList();
		System.out.println(list);
		for (UserVO user : list) {
			LocalDateTime now = LocalDateTime.now();
			LocalDateTime last = user.getMember_last_login_time();
			System.out.println(now +" "+ last);
			Duration duration = Duration.between(last, now);
			System.out.println("member diff " + duration.toString());
			if (duration.toMinutes() >= 10) {
				userMapper.unlockUserLogin(user.getMember_id());
				System.out.println("user unlock id : " + user.getMember_id());
			}
		}

	}

	public void adminUnlockUserLogin(String name){
		int res = userMapper.unlockUserLogin(name);
		if(res > 0){
			System.out.println("success");
		}else{
			System.out.println("failed");
		}
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("유저 서비스 접근 확인");
		System.out.println(username);
		UserVO resultVO = userMapper.login(UserVO.builder().member_id(username).build());
		System.out.println(resultVO);
		if (resultVO == null) {
			throw new UsernameNotFoundException(username + " 사용자가 존재하지 않습니다");
		}

		userMapper.loginCountInc(resultVO);
		
		// System.out.println(resultVO);
		return resultVO;

	}

    public UserVO getView(String user) {
		UserVO userVO = userMapper.getView(user);
		return userVO;
    }

    public boolean checkID(String id) {
		UserVO user = userMapper.checkID(id);
		System.out.println(user);
		if(user == null){
			return true;
		}
        return false;
    }

	public List<UserHobbyVO> gethobbyList() {
		
		return userMapper.gethobbyList();
	
	}

	public int register(UserVO userVO, UserHobbyVO userHobbyVO) {
		String str = userHobbyVO.getHobbyname(); //축구 , 야구 , 영화
		String[] hb = str.split(",\\s*");
		
		userVO.setMember_pwd(passwordEncoder.encode(userVO.getMember_pwd()));
		System.out.println(userVO.getMember_pwd());
		int n = userMapper.register(userVO);
		if(n>1){
			for(String item : hb){
				userMapper.reg_userhobby(userVO.getMember_id(), item);
			}
			
		}
		return n;
	}

    public int deleteAdmin(UserVO user) {
		int n = 0;
		String[] userid = user.getMember_id().split(",\\s*");
		for(String item : userid){
			if(item.equals("admin")){
				continue;
			}
			n += userMapper.delete(item);
		}
		return n;
    }



}
