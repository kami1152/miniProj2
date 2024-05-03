package com.example.demo.util;

import com.example.demo.data.LocalUrl;
import com.example.demo.vo.FileVO;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public class FileDownloadUitl implements LocalUrl {
    
    public ResponseEntity<Resource> downloadFile(FileVO fileVO)
     throws IOException{

        System.out.println("indownload -> " + fileVO);
        Path root = Paths.get(localpath);
        
        Path file = root.resolve(fileVO.getUploadPath());

        Resource resource = new UrlResource(file.toUri());

        if (resource.exists()  && resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            throw new RuntimeException("Could not read the file!");
        }

    }
}
