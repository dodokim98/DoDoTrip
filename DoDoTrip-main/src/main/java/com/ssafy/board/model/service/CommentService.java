package com.ssafy.board.model.service;

import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.board.model.dto.CommentDto;

import java.sql.SQLException;
import java.util.List;

public interface CommentService {
    // 댓글 등록, 댓글 수정, 댓글 삭제, 댓글 목록, 댓글 조회
    void registComment(CommentDto commentDto) throws SQLException;

    void modifyComment(CommentDto commentDto) throws Exception;

    void deleteComment(int commentNo) throws Exception;

    List<CommentDto> getCommentList(int boardArticleNo) throws Exception;

    CommentDto getComment(int commentNo) throws Exception;
}
