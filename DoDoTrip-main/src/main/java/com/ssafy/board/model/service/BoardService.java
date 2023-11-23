package com.ssafy.board.model.service;

import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.board.model.dto.BoardListDto;

import java.util.Map;

public interface BoardService {

    void registBoard(BoardDto boardDto) throws Exception;

    BoardListDto getBoardList(Map<String, String> map) throws Exception;

    BoardDto getBoardDetail(int articleNo) throws Exception;

    void modifyBoard(BoardDto boardDto) throws Exception;

    void deleteBoard(int articleNo) throws Exception;

     void updateBoardHit(int articleNo) throws Exception;

}
