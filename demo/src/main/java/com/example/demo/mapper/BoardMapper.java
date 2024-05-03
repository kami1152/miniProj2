package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.vo.BoardVO;
import com.example.demo.vo.FileVO;
import com.example.demo.vo.PageRequestVO;

@Mapper
public interface BoardMapper {
    public int boardCount();
    public List<BoardVO> getList(PageRequestVO pageRequestVO);
    public int insert(BoardVO boardVO);
    public BoardVO getBoardNumber(String name);
    public int filesaver(FileVO newfile);
    
}
