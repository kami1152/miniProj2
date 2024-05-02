package com.example.demo.vo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestVO {

    private String link;

    private String searchKey;
    private String searchType;

    public String getLink(){
        if(link == null){
            StringBuilder builder = new StringBuilder();

            if (this.searchKey != null && this.searchKey.length() != 0) {
				try {
					builder.append("&searchKey=" + URLEncoder.encode(this.searchKey,"UTF-8"));
                    setType();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
            link = builder.toString();
        }
        return link;
    }

    public void setType(){
        String temp = this.searchType;
        if(temp.equals("이름")){
            this.searchType = "member_id";
        }
        if(temp.equals("성별")){
            this.searchType = "member_gender";
        }
        if(temp.equals("전화번호")){
            this.searchType = "member_phone_number";
        }
        if(temp.equals("권한")){
            this.searchType = "member_privilege";
        }
        if(temp.equals("잠금여부")){
            this.searchType = "member_account_locked";
        }
        
    }
}
