package com.example.demo.service;

import java.util.*;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.example.demo.vo.BoardVO;
import com.example.demo.vo.PageRequestVO;
import com.example.demo.vo.PageResponseVO;

import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

import com.example.demo.mapper.BoardMapper;

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

        //System.out.println(pageResponseVO.getStart());

        return pageResponseVO;

    }

}
