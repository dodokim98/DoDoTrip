package com.ssafy.qna.controller;

import com.ssafy.qna.model.dto.QnaDto;
import com.ssafy.qna.model.dto.QnaListDto;
import com.ssafy.qna.model.service.QnaService;
import com.ssafy.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.util.Map;

@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST}, maxAge = 6000)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class QnaController {
    //수정 없음

    private final QnaService qnaService;
    private final JWTUtil jwtUtil;

    //질문 등록
    @PostMapping("/qnas/question")
    public ResponseEntity<?> registerQna(@RequestBody QnaDto qnaDto, HttpServletRequest req) throws Exception {
        try {
            String email = jwtUtil.getUserEmail(req.getHeader("Authorization"));
            qnaDto.setMemberUserEmail(email);
            qnaService.registerQna(qnaDto);
            return new ResponseEntity<Void>(HttpStatus.CREATED);

        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    //답변 등록
    @PutMapping("/qnas/answer")
    public ResponseEntity<?> registerAnswer(@RequestBody QnaDto qnaDto, HttpServletRequest request) throws Exception {
        log.info("registerAnswer - 호출 {}", qnaDto);

        qnaService.registerAnswer(qnaDto);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    //목록
    @GetMapping("/qnas")
    public ResponseEntity<?> getQnaList(@RequestParam Map<String, String> map) throws Exception {
        log.info("listQnA map - {}", map);
        try {
            QnaListDto qnaListDto = qnaService.getQnaList(map);
            HttpHeaders header = new HttpHeaders();
            header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
            return ResponseEntity.ok().headers(header).body(qnaListDto);

        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    //조회
    @GetMapping("/qnas/{qnano}")
    public ResponseEntity<QnaDto> getQnaDetail(@PathVariable("qnano") int qnaNo) throws Exception {
        log.info("getQnaDetail - 호출 : " + qnaNo);

        return new ResponseEntity<QnaDto>(qnaService.getQnaDetail(qnaNo), HttpStatus.OK);
    }

    //삭제
    @DeleteMapping("/qnas/{qnano}")
    public ResponseEntity<?> deleteQna(@PathVariable("qnano") int qnaNo, HttpServletRequest request) throws Exception {
        log.info("deleteQna - 호출");
        String header = request.getHeader("Authorization");
        log.info("header : {}", header);
        String email = jwtUtil.getUserEmail(header); //작성자의 이메일
        String role = jwtUtil.getUserRole(header); //작성자의 권한
        QnaDto qnaDto = qnaService.getQnaDetail(qnaNo);
        log.info("email : {}", email);
        log.info("role : {}", role);
        //작성자이거나, 관리자면 삭제 가능
        if (email.equals(qnaDto.getMemberUserEmail()) || role.equals("ADMIN")) {
            qnaService.deleteQna(qnaNo);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<Void>(HttpStatus.FORBIDDEN);

    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
