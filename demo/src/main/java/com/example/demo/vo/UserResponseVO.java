package com.example.demo.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.*;


@Getter
@ToString
public class UserResponseVO<E> {
    

    private List<E> list;


    @Builder(builderMethodName = "withAll")
    public UserResponseVO(List<E> list){
        this.list = list;
    }
}
