package com.ssafy.qna.model.dto;

import lombok.Data;

@Data
public class QnaDto {
    /**
     * 관리자의 답변이 있다면 보여질 화면
     */
    private int qnaNo;

    private String question; //질문

    private String answer; //답변(관리자만 가능)

    private String memberUserEmail; //외래키(질문자)

}
