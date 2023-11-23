package com.ssafy.attraction.model.dto;

import lombok.Data;

@Data
public class AttractionLikeDto {
	private int likeNo;
	private String registerDate;
	private int attractionInfoContentId;
	private String memberUserEmail; //외래키
}
