package com.example.demo.data;

import lombok.Getter;


@Getter
public enum UserPrivilege {

    LEVEL1("user"), 
    LEVEL5("admin");

    private String privilege;
    private UserPrivilege(String privilege){
        this.privilege = privilege.toUpperCase();
    }
    
}
