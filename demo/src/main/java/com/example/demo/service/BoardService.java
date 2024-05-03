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

    public int insert(String title, String content, String username) {
        BoardVO boardVO = new BoardVO();
        System.out.println("flag 1");
        boardVO.setBtitle(title);
        boardVO.setBcontent(content);
        boardVO.setMember_id(username);
        System.out.println("flag 2");
        int temp =boardMapper.insert(boardVO);
        System.out.println("temp:" + temp);
        return temp;
    }

    public int insertfile(String title, MultipartFile file) {
       
        FileSaveUtil fileSaveUtil = new FileSaveUtil();
        //local save
        BoardVO bnos = boardMapper.getBoardNumber(title);
        int bno  = bnos.getBno();
        FileVO newfile = fileSaveUtil.fileSaver(String.valueOf(bno), file);
      
    
        newfile.setFilename(file.getOriginalFilename());
        System.out.println("newfile -> " + newfile);
        int n = boardMapper.filesaver(newfile);
       
        return n;
    }

    public BoardVO getView(int bno) {
        BoardVO board = boardMapper.getView(bno);
        return board;
    }

    public FileVO getFilename(int bno) {
        FileVO file = boardMapper.getFilename(bno);
        return file;
    }



}
