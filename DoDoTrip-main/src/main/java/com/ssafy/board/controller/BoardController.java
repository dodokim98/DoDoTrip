package com.ssafy.board.controller;

import java.nio.charset.Charset;
import java.util.Map;

import com.ssafy.board.model.dto.BoardListDto;
import com.ssafy.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.board.model.service.BoardService;

import javax.servlet.http.HttpServletRequest;

@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST}, maxAge = 6000)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class BoardController {
    private final BoardService boardService;
    private final JWTUtil jwtUtil;

    @PostMapping("/boards")
    public ResponseEntity<?> registBoard(@RequestBody BoardDto boardDto, HttpServletRequest req) throws Exception {
        try {
            String header = req.getHeader("Authorization");
            String email = jwtUtil.getUserEmail(header);
            String role = jwtUtil.getUserRole(header);

            boardDto.setMemberUserEmail(email);
            boardService.registBoard(boardDto);
            return new ResponseEntity<Void>(HttpStatus.CREATED);

        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/boards")
    public ResponseEntity<?> getBoardList(@RequestParam Map<String, String> map) throws Exception {
        log.info("listArticle map - {}", map);
        try {
            BoardListDto boardListDto = boardService.getBoardList(map);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            return ResponseEntity.ok().headers(header).body(boardListDto);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/boards/{articleno}")
    public ResponseEntity<BoardDto> getBoardDetail(@PathVariable("articleno") int articleNo, HttpServletRequest req) throws Exception {
        log.info("getArticle - 호출 : " + articleNo);

        boardService.updateBoardHit(articleNo);
        return new ResponseEntity<BoardDto>(boardService.getBoardDetail(articleNo), HttpStatus.OK);
    }

    //수정
    @PutMapping("/boards")
    public ResponseEntity<?> modifyBoard(@RequestBody BoardDto boardDto, HttpServletRequest request) throws Exception {
        log.info("modifyBoard - 호출 {}", boardDto);
        String header = request.getHeader("Authorization");
        log.info("header : {}", header);

        String email = jwtUtil.getUserEmail(header); //작성자의 이메일
        String role = jwtUtil.getUserRole(header); //작성자의 권한

        if (email.equals(boardDto.getMemberUserEmail()) || role.equals("ADMIN")) {
            boardService.modifyBoard(boardDto);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
    }

    //삭제
    @DeleteMapping("/boards/{articleno}")
    public ResponseEntity<?> deleteBoard(@PathVariable("articleno") int articleNo, HttpServletRequest request) throws Exception {
        log.info("deleteBoard - 호출");
        String header = request.getHeader("Authorization");
        log.info("header : {}", header);

        String email = jwtUtil.getUserEmail(header); //작성자의 이메일
        String role = jwtUtil.getUserRole(header); //작성자의 권한
        BoardDto boardDto = boardService.getBoardDetail(articleNo);

        if (email.equals(boardDto.getMemberUserEmail()) || role.equals("ADMIN")) {
            boardService.deleteBoard(articleNo);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
