package com.ssafy.board.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.board.model.dto.CommentDto;

@Mapper
public interface CommentMapper {
    List<CommentDto> getCommentList(int boardArticleNo) throws SQLException;

    void registComment(CommentDto commentDto) throws SQLException;

    void modifyComment(CommentDto commentDto) throws SQLException;

    void deleteComment(int commentNo) throws SQLException;

    CommentDto getComment(int commentNo) throws SQLException;
}
