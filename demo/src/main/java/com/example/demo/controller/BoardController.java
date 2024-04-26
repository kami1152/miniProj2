package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.mapper.BoardMapper;
import com.example.demo.service.BoardService;
import com.example.demo.service.CodeService;
import com.example.demo.vo.PageRequestVO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {

    
    private final BoardService boardService;
    private final BoardMapper boardMapper;
    private final CodeService codeService;

    //BoardMapper boardMapper;
    @RequestMapping("/list")
    public String list(Model model,@Valid PageRequestVO pageRequestVO, BindingResult bindingResult) throws Exception{
        //System.out.println(boardMapper.boardCount());

        System.out.println(pageRequestVO.toString());
        if(bindingResult.hasErrors()){
            pageRequestVO = PageRequestVO.builder().build();
        }



        model.addAttribute("pageResponseVO", boardService.getList(pageRequestVO));
        model.addAttribute("sizes", codeService.getList());
        return "board/list";
    }
}
