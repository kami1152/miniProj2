package com.example.demo.service;

import java.util.*;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.vo.BoardVO;
import com.example.demo.vo.FileVO;
import com.example.demo.vo.PageRequestVO;
import com.example.demo.vo.PageResponseVO;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

import com.example.demo.mapper.BoardMapper;
import com.example.demo.util.FileSaveUtil;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public PageResponseVO<BoardVO> getList(PageRequestVO pageRequestVO) {

        List<BoardVO> list = boardMapper.getList(pageRequestVO);

        int total = boardMapper.boardCount();
        System.out.println(total);

        PageResponseVO<BoardVO> pageResponseVO = PageResponseVO.<BoardVO>withAll()
                .list(list)
                .total(total)
                .size(pageRequestVO.getSize())
                .pageNo(pageRequestVO.getPageNo())
                .build();

        // System.out.println(pageResponseVO.getStart());

        return pageResponseVO;

    }

    public BoardVO insert(String title, String content, String username) {
        BoardVO boardVO = new BoardVO();

        boardVO.setBtitle(title);
        boardVO.setBcontent(content);
        boardVO.setMember_id(username);

        int n = boardMapper.insert(boardVO);
        BoardVO newboardVO = boardMapper.getBoardNumber(title);
        if(n>0){
            return newboardVO;
        }
        return null;
    }

    public int insertfile(String bno, MultipartFile file) {
       
        FileSaveUtil fileSaveUtil = new FileSaveUtil();
        //local save
        FileVO newfile = fileSaveUtil.fileSaver(bno, file);
      
        if(newfile == null){
            return 0;
        }else{
            newfile.setFilename(file.getOriginalFilename());
        }
        int n = boardMapper.filesaver(newfile);
       
        return n;
    }



}
