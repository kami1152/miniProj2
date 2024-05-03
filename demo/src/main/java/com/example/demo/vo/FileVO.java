package com.example.demo.vo;

import lombok.Data;

@Data
public class FileVO {
    private String bno;
    private String uploadPath; // 파일이 저장된 경로
    private String filename;
}
