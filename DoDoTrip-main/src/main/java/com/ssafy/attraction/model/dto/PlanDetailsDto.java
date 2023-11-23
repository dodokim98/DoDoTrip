package com.ssafy.attraction.model.dto;

import lombok.Data;

@Data
public class PlanDetailsDto {
    private int planNo;
    private String createDate;
    private String startDate;
    private String endDate;
    private String title;
    private int order;
    private int planAttractionNo;
    private String planDate;
    private String attractionTitle;
    private String attractionAddr1;
    private String attractionAddr2;

    // getters and setters
}