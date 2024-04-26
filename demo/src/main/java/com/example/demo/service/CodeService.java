package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.mapper.CodeMapper;
import com.example.demo.vo.CodeVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CodeService {
    private final CodeMapper codeMapper;
    
    public List<CodeVO> getList(){
        List<CodeVO> list = codeMapper.getList();
        System.out.println("codelist : " + list);
        return list;
    }

}
