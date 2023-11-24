package com.ssafy.attraction.model.dto;

import lombok.Data;

@Data
public class PlanAttractionListDto {
    private int planAttractionNo;
    private int order;
    private int attractionInfoContentId;
    private int planPlanNo;
    private String planDate;
    private String title; // 추가
    private String addr1; // 추가
    private String addr2; // 추가
}
