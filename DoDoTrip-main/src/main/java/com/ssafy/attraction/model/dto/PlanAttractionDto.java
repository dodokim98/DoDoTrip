package com.ssafy.attraction.model.dto;

import lombok.Data;

@Data
public class PlanAttractionDto {
	//중간테이블

	private int planAttractionNo;

	private int order; //순서

	private String planDate; //계획날짜

 	private int attractionInfoContentId; //attraction_info 외래키

	private int planPlanNo; //plan 외래키
}
