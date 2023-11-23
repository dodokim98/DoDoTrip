package com.ssafy.board.controller;

import com.ssafy.board.model.dto.CommentDto;
import com.ssafy.board.model.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST}, maxAge = 6000)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class CommentController {

	private final CommentService commentService;

	@GetMapping("{boardArticleNo}/comments")
	public List<CommentDto> getCommentList(@PathVariable("boardArticleNo") int boardArticleNo) throws Exception{
		log.info("getCommentList - 호출 : " + boardArticleNo);
		return commentService.getCommentList(boardArticleNo);
	}

	@PostMapping("/comments")
	public void registComment(@RequestBody CommentDto commentDto) throws  Exception {
		log.info("registComment - 호출 : " + commentDto);
		commentService.registComment(commentDto);
	}

	@PutMapping("/comments")
	public void modifyComment(@RequestBody CommentDto commentDto) throws  Exception {
		log.info("modifyComment - 호출 : " + commentDto);
		commentService.modifyComment(commentDto);
	}

	@DeleteMapping("/comments/{commentNo}")
	public void deleteComment(@PathVariable int commentNo) throws  Exception{
		log.info("deleteComment - 호출 : " + commentNo);
		commentService.deleteComment(commentNo);
	}

	@GetMapping("/comments/detail/{commentNo}")
	public CommentDto getComment (@PathVariable int commentNo) throws  Exception{
		log.info("getComment - 호출 : " + commentNo);
		return commentService.getComment(commentNo);
	}

}
