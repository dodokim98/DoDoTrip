package com.ssafy.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssafy.member.model.service.MemberService;
import com.ssafy.util.JWTUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.member.model.dto.MemberDto;

import lombok.RequiredArgsConstructor;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Log4j2
public class MemberController {

    private final MemberService memberService;
    private final JWTUtil jwtUtil;

    //회원 가져오기
    @GetMapping("/member/{userEmail}")
    public ResponseEntity<Map<String, Object>> findMemberByEmail(@PathVariable("userEmail") String userEmail, HttpServletRequest req) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        System.out.println(req.getHeader("Authorization"));
        if (jwtUtil.checkToken(req.getHeader("Authorization"))) {
            log.info("사용 가능한 토큰!!!");
            try {
//				로그인 사용자 정보.
                MemberDto memberDto = memberService.findMemberByEmail(userEmail);
                resultMap.put("userInfo", memberDto);
                status = HttpStatus.OK;
            } catch (Exception e) {
                log.error("정보조회 실패 : {}", e);
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            log.error("사용 불가능 토큰!!!");
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    //회원 로그인
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginMember(@RequestBody MemberDto memberDto, HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            MemberDto loginUser = memberService.loginMember(memberDto);
            log.debug("user: {}", loginUser);

            if (loginUser != null) {
                String accessToken = jwtUtil.createAccessToken(loginUser.getUserEmail(), loginUser.getRole());
                String refreshToken = jwtUtil.createRefreshToken(loginUser.getUserEmail(), loginUser.getRole());
                log.debug("access token : {}", accessToken);
                log.debug("refresh token : {}", refreshToken);

//                발급받은 refresh token을 DB에 저장.
                memberService.saveRefreshToken(loginUser.getUserEmail(), loginUser.getRole(), refreshToken);

//                JSON으로 token 전달.
                resultMap.put("access-token", accessToken);
                resultMap.put("refresh-token", refreshToken);
                status = HttpStatus.CREATED;
                log.info(loginUser);

            } else {
                resultMap.put("message", "아이디 또는 패스워드를 확인해주세요.");
                status = HttpStatus.UNAUTHORIZED;
            }
        } catch (Exception e) {
            log.debug("로그인 에러 발생 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);

    }

    //회원 등록
    @PostMapping("/signup")
    public ResponseEntity<?> registerMember(@RequestBody MemberDto memberDto) throws NoSuchAlgorithmException {
        log.debug("memberDto : {}", memberDto);

        try {
            memberService.registerMember(memberDto);
            return new ResponseEntity<Void>(HttpStatus.CREATED);

        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    //회원 수정
    @PutMapping("/member")
    public ResponseEntity<?> updateMember(@RequestBody MemberDto memberDto) {
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            memberService.updateMember(memberDto);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    //회원 탈퇴
    @DeleteMapping("/member/{userEmail}")
    public void deleteMember(@PathVariable("userEmail") String userEmail) {
        memberService.deleteMember(userEmail);
    }

    //로그아웃
    @GetMapping("/logout/{userEmail}")
    public ResponseEntity<?> removeToken(@PathVariable("userEmail") String userEmail) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            memberService.deleRefreshToken(userEmail);
            status = HttpStatus.OK;
        } catch (Exception e) {
            log.error("로그아웃 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);

    }

    //리프레시 토큰 발급
    @PostMapping("/member/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody MemberDto memberDto, HttpServletRequest request)
            throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        String token = request.getHeader("refreshToken");
        log.debug("token : {}, memberDto : {}", token, memberDto);
        if (jwtUtil.checkToken(token)) {
            if (token.equals(memberService.getRefreshToken(memberDto.getUserEmail()))) {
                String accessToken = jwtUtil.createAccessToken(memberDto.getUserEmail(), memberDto.getRole());
                log.debug("token : {}", accessToken);
                log.debug("정상적으로 액세스토큰 재발급!!!");
                resultMap.put("access-token", accessToken);
                status = HttpStatus.CREATED;
            }
        } else {
            log.debug("리프레쉬토큰도 사용불가!!!!!!!");
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    private ResponseEntity<String> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<String>("Error : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
