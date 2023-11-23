package com.ssafy.qna.model.mapper;

import com.ssafy.qna.model.dto.QnaDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface QnaMapper {

    //질문 등록
    void registerQna(QnaDto qnaDto) throws Exception;

    //답변 등록
    void registerAnswer(QnaDto qnaDto) throws Exception;

    //리스트
    List<QnaDto> getQnaList(Map<String, Object> param) throws Exception;

    int getTotalQnaCount(Map<String, Object> map) throws SQLException;

    //상세보기(2가지 -> 답변이 있거나, 없는 경우(없다면 아예 화면에 나오지 않게))
    QnaDto getQnaDetail(int qnaNo) throws Exception;

    //삭제
    void deleteQna(int qnaNo) throws Exception;
}
