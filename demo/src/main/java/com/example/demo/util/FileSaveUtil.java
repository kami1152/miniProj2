package com.example.demo.util;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.data.LocalUrl;
import com.example.demo.vo.FileVO;

import java.io.*;
import java.nio.file.*;

public class FileSaveUtil implements LocalUrl {
    

    public FileVO fileSaver(String bno, MultipartFile file){
        System.out.println("file save step 1");
        FileVO custom = new FileVO();
        File directory = new File(localpath);
        if (!directory.exists()) {
            if (!directory.mkdir()) {
                return null;  // 디렉토리 생성 실패
            }
        }
        String origin_FileName = file.getOriginalFilename();
        String fileName =java.util.UUID.randomUUID().toString()+"_"+origin_FileName;

        System.out.println("file save step 2 " + fileName);
        Path path = Paths.get(localpath+fileName);
        System.out.println("file save step 3 " + path);

        try {
            file.transferTo(path);

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
           
            e.printStackTrace();
        }

        custom.setBno(bno);
        custom.setUploadPath(fileName);

        return custom;

    }
}
