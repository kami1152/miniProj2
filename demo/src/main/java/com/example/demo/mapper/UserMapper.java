package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import java.util.*;

import com.example.demo.vo.UserHobbyVO;
import com.example.demo.vo.UserRequestVO;
import com.example.demo.vo.UserVO;

@Mapper
public interface UserMapper {
    
    UserVO login(UserVO userVO);


    //마지막 시간 카운트
    int updateMemberLastLogin(String email);
    //유저 카운트
    void loginCountInc(UserVO userVO);
    void loginCountClear(String email);

    List<UserVO> getList(UserRequestVO userVO); 
    List<UserVO> LockUserList(); 
    int unlockUserLogin(String email);


    UserVO getView(String user);


    UserVO checkID(String id);

    List<UserHobbyVO> gethobbyList();


    int register(UserVO userVO);


    void reg_userhobby(String member_id, String item);


    int delete(String item);

}
