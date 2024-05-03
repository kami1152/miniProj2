package com.example.demo.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
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
import com.example.demo.util.FileDownloadUitl;
import com.example.demo.vo.BoardVO;
import com.example.demo.vo.FileVO;
import com.example.demo.vo.PageRequestVO;
import com.example.demo.vo.UserVO;

import java.io.IOException;
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

    //private final FileDownloadUitl fileDownloadUitl;

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
        int boardVO = boardService.insert(title, content, username);
        System.out.println("this ready " + boardVO);
        if (boardVO > 0) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    boardService.insertfile(title, file);
                }
            }
        }

        if (boardVO > 0) {
            map.put("status", 0);
            map.put("msg", "작성에 성공하였습니다.");
        } else {
            map.put("status", -99);
            map.put("msg", "작성에 실패하였습니다.");
        }
        return map;
    }


    @RequestMapping("/BoardInfo")
    @ResponseBody
    public Map<String, Object> insert(@RequestBody BoardVO boardVO) {

        Map<String, Object> map = new HashMap<>();

        BoardVO board = boardService.getView(boardVO.getBno());

        FileVO fileVO = boardService.getFilename(boardVO.getBno());

        System.out.println(boardVO);
        System.out.println(fileVO);

        if(fileVO != null){
            map.put("jsonFile",fileVO);
        }   else{
            map.put("jsonFile",null);
        }

        if(board != null){
            map.put("status",0);
            map.put("jsonBoard",board);
        }else{
            map.put("status", 99);
            map.put("msg", "good");
        }
        return map;
    }


    @RequestMapping("/download")
    @ResponseBody
    public ResponseEntity<Resource> download(
        @RequestParam("bno") String bno,
        @RequestParam("filename") String filename
    ) {

        FileVO file = boardService.getFilename(Integer.parseInt(bno));
        //System.out.println(file);
        try {
            FileDownloadUitl fileDownloadUitl = new FileDownloadUitl();

            ResponseEntity<Resource> temp =  fileDownloadUitl.downloadFile(file);
            System.out.println("good " +temp);

            return temp;
        }  catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.notFound()
                                 .build();
        } catch(IOException e){
            e.printStackTrace();
            return ResponseEntity.notFound()
            .build();
        }

    }

}
