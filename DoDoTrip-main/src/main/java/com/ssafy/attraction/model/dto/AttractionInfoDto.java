package com.ssafy.attraction.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AttractionInfoDto {
    private int contentId;
    private int contentTypeId;
    private String title;
    private String addr1;
    private String addr2;
    private String zipCode;
    private String tel;
    private String firstImage;
    private String firstImage2;
    private int readCount;
    private int sidoCode;
    private int gugunCode;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String mLevel;
    private int likeNo;
}
