package com.ssafy.attraction.model.dto;

import lombok.Data;

@Data
public class PlanDto {	
	private int planNo;
	
	private String createDate;
	
	private String startDate;
	
	private String endDate;
	
	private String title;
	
	private String content;

	private String memberUserEmail; //외래키
}
