package com.ssafy.board.model.service.impl;

import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.board.model.dto.CommentDto;
import com.ssafy.board.model.mapper.CommentMapper;
import com.ssafy.board.model.service.CommentService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
	private final CommentMapper commentMapper;

	public CommentServiceImpl(CommentMapper commentMapper) {
		super();
		this.commentMapper = commentMapper;
	}

	@Override
	public void registComment(CommentDto commentDto) throws SQLException {
		commentMapper.registComment(commentDto);
	}


	@Override
	public void deleteComment(int commentNo) throws Exception {
		commentMapper.deleteComment(commentNo);
	}

	@Override
	public CommentDto getComment(int commentNo) throws Exception {
		return commentMapper.getComment(commentNo);
	}

	@Override
	public List<CommentDto> getCommentList(int boardArticleNo) throws Exception {
		return commentMapper.getCommentList(boardArticleNo);
	}

	@Override
	public void modifyComment(CommentDto commentDto) throws Exception {
		commentMapper.modifyComment(commentDto);
	}

}
