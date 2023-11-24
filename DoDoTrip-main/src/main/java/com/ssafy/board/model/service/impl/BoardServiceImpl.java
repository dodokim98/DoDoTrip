package com.ssafy.board.model.service.impl;

import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.board.model.dto.BoardListDto;
import com.ssafy.board.model.mapper.BoardMapper;
import com.ssafy.board.model.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardMapper boardMapper;
	@Override
	public BoardListDto getBoardList(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int sizePerPage = Integer.parseInt(map.get("spp") == null ? "10" : map.get("spp"));
		int start = currentPage * sizePerPage - sizePerPage;
		param.put("start", start);
		param.put("listsize", sizePerPage);

		String key = map.get("key");
		param.put("key", key == null ? "" : key);
		if ("user_id".equals(key))
			param.put("key", key == null ? "" : "b.member_user_email");
		List<BoardDto> list = boardMapper.getBoardList(param);

		int totalArticleCount = boardMapper.getTotalArticleCount(param);
		int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

		BoardListDto boardListDto = new BoardListDto();
		boardListDto.setArticles(list);
		boardListDto.setCurrentPage(currentPage);
		boardListDto.setTotalPageCount(totalPageCount);

		return boardListDto;
	}

	@Override
	public BoardDto getBoardDetail(int articleNo) throws Exception {
		boardMapper.updateBoardHit(articleNo); //조회수 증가
		return boardMapper.getBoardDetail(articleNo);
	}

	@Override
	public void deleteBoard(int articleNo) throws Exception {
		boardMapper.deleteBoard(articleNo);
	}

	@Override
	public void updateBoardHit(int articleNo) throws Exception {
		boardMapper.updateBoardHit(articleNo);
	}

	@Override
	public void modifyBoard(BoardDto boardDto) throws Exception {
		boardMapper.modifyBoard(boardDto);
	}

	@Override
	public void registBoard(BoardDto boardDto) throws Exception {
		boardMapper.registBoard(boardDto);
	}
	
}
