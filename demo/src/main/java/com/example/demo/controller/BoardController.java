package com.example.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.mapper.BoardMapper;
import com.example.demo.service.BoardService;
import com.example.demo.service.CodeService;
import com.example.demo.vo.BoardVO;
import com.example.demo.vo.FileVO;
import com.example.demo.vo.PageRequestVO;
import com.example.demo.vo.UserVO;

import java.util.*;

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

    // BoardMapper boardMapper;
    @RequestMapping("/list")
    public String list(Model model, @Valid PageRequestVO pageRequestVO, BindingResult bindingResult) throws Exception {
        // System.out.println(boardMapper.boardCount());

        // UserVO userVO = (UserVO) authentication.getPrincipal();

        // System.out.println("now board user : " + userVO);
        if (bindingResult.hasErrors()) {
            pageRequestVO = PageRequestVO.builder().build();
        }

        model.addAttribute("pageResponseVO", boardService.getList(pageRequestVO));
        model.addAttribute("sizes", codeService.getList());

        return "board/list";
    }

    @RequestMapping("/insertForm")
    public String insertForm(Model model) {

        return "board/insertForm";
    }

    @RequestMapping("/insert")
    @ResponseBody
    public Map<String, Object> insert(
            @RequestParam("title") String title,
            @RequestParam("content") String content,
            @RequestParam("attachments") MultipartFile[] files,
            @RequestParam("bwriter") String username) {

        Map<String, Object> map = new HashMap<>();

        System.out.println("Title: " + title);
        System.out.println("Content: " + content);
        System.out.println("username: " + username);
        System.out.println("Number of files: " + files.length);
        BoardVO boardVO = boardService.insert(title, content, username);
        if (boardVO != null) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    boardService.insertfile(String.valueOf(boardVO.getBno()), file);
                }
            }
        }
        System.out.println(boardVO);

        if (boardVO != null) {
            map.put("status", 0);
            map.put("msg", "작성에 성공하였습니다.");
        } else {
            map.put("status", -99);
            map.put("msg", "작성에 실패하였습니다.");
        }
        return map;
    }

}
