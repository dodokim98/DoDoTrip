package com.ssafy.member.model.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.member.model.dto.MemberDto;
import com.ssafy.member.model.mapper.MemberMapper;

import lombok.RequiredArgsConstructor;

import java.security.NoSuchAlgorithmException;
import java.util.*;


@Service
@RequiredArgsConstructor
@Log4j2
public class MemberService implements UserDetailsService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;

    public void registerMember(MemberDto memberDto) throws NoSuchAlgorithmException {
        String encryptPwd = passwordEncoder.encode(memberDto.getPassword());
        memberDto.setUserPass(encryptPwd);

        memberMapper.registerMember(memberDto);
    }

    public void deleteMember(String userId) {
        memberMapper.deleteMember(userId);
    }


    public MemberDto findMemberByEmail(String userEmail) {
        return memberMapper.findMemberByEmail(userEmail);
    }


    public MemberDto loginMember(MemberDto memberDto) {
        //파라미터 memberDto은 userEmail과 userPass 값만 들어있음
        MemberDto memberDto1 = memberMapper.findMemberByEmail(memberDto.getUserEmail()); //PK값을 가지고 DB에 있는 저장된 값을 가져옴
        String encodedPassword = memberDto1.getUserPass();
        String rawPassword = memberDto.getUserPass();
        log.info("memberDto1 : {}", memberDto1);
        System.out.println(encodedPassword);
        System.out.println(rawPassword);
        boolean isOk = passwordEncoder.matches(rawPassword, encodedPassword);
        System.out.println(isOk);

        if (passwordEncoder.matches(rawPassword, encodedPassword)) {
            return memberDto1;
        }
        return null;
    }


    public void updateMember(MemberDto memberDto) {
        String encryptPwd = passwordEncoder.encode(memberDto.getPassword());
        memberDto.setUserPass(encryptPwd);

        memberMapper.updateMember(memberDto);

    }

    public void saveRefreshToken(String userEmail, String role, String refreshToken) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userEmail", userEmail);
        map.put("userRole", role);
        map.put("token", refreshToken);
        memberMapper.saveRefreshToken(map);
    }


    public Object getRefreshToken(String userEmail) throws Exception {
        return memberMapper.getRefreshToken(userEmail);
    }


    public void deleRefreshToken(String userEmail) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userEmail", userEmail);
        map.put("token", null);
        memberMapper.deleteRefreshToken(map);
    }


    public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        //username : 이메일을 뜻함
        log.info("MemberService loadUserByUsername " + userEmail);
        MemberDto memberDto = memberMapper.findMemberByEmail(userEmail);

        if (memberDto != null) {
            memberDto.setAuthorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_" + memberDto.getRole())));

            return memberDto;
        }
        return null;
    }
}
