package com.ssafy.attraction.model.service;

import com.ssafy.attraction.model.dto.AttractionInfoDto;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

public interface AttractionInfoService {

    List<AttractionInfoDto> selectByContentType(int contentTypeId);

    Map<String, Object> getAttractionLst(Map<String, Object> param);
}
