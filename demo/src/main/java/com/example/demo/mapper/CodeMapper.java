package com.example.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.vo.CodeVO;

@Mapper
public interface CodeMapper {

    public List<CodeVO> getList();
    
}
