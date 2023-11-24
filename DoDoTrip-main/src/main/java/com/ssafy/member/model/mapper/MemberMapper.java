package com.ssafy.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.member.model.dto.MemberDto;

import java.sql.SQLException;
import java.util.Map;

@Mapper
public interface MemberMapper {

    void registerMember(MemberDto memberDto);

    void deleteMember(String userEmail);

    MemberDto findMemberByEmail(String userEmail);

    MemberDto loginMember(MemberDto memberDto);

    void updateMember(MemberDto memberDto);

//    =========== 토큰 ===========
    void saveRefreshToken(Map<String, String> map) throws SQLException;

    Object getRefreshToken(String userid) throws SQLException;

    void deleteRefreshToken(Map<String, String> map) throws SQLException;

}
