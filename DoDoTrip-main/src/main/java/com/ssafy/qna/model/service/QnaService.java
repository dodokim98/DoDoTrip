package com.ssafy.qna.model.service;


import com.ssafy.qna.model.dto.QnaDto;
import com.ssafy.qna.model.dto.QnaListDto;

import java.util.List;
import java.util.Map;

public interface QnaService {

    //질문 등록
    void registerQna(QnaDto qnaDto) throws Exception;

    //답변 등록
    void registerAnswer(QnaDto qnaDto) throws Exception;

    //리스트
    QnaListDto getQnaList(Map<String, String> map) throws Exception;

    //상세보기(2가지 -> 답변이 있거나, 없는 경우)
    QnaDto getQnaDetail(int qnaNo) throws Exception;

    //삭제
    void deleteQna(int qnaNo) throws Exception;
}
