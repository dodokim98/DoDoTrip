package com.ssafy.board.model.dto;

import lombok.Data;

@Data
public class CommentDto {
    private int commentNo;
    private String content;
    private String createTime;
    private int boardArticleNo;
    private String memberUserEmail;
}
