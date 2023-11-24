package com.ssafy.attraction.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class SidoGugunCodeDto {

	private String sidoCode;

	private String sidoName;

	private String gugunCode;

	private String gugunName;
	
}
