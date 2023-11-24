package com.ssafy.board.model.mapper;

import com.ssafy.board.model.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    void registBoard(BoardDto boardDto) throws SQLException;

    List<BoardDto> getBoardList(Map<String, Object> param) throws SQLException;

    int getTotalArticleCount(Map<String, Object> map) throws SQLException;

    BoardDto getBoardDetail(int articleNo) throws SQLException;

    void modifyBoard(BoardDto boardDto) throws Exception;

    void updateBoardHit(int articleNo) throws Exception;

    void deleteBoard(int articleNo) throws SQLException;

}
