package com.ssafy.board.model.dto;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
public class BoardDto {
	private int articleNo;
	private String subject;
	private String content;
	private int hit;
	private String registerTime;
	private String memberUserEmail; //외래키
}	
