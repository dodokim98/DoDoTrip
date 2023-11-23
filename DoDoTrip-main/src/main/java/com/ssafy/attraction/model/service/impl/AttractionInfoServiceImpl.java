package com.ssafy.attraction.model.service.impl;

import com.ssafy.attraction.model.dto.AttractionInfoDto;
import com.ssafy.attraction.model.mapper.AttractionInfoMapper;
import com.ssafy.attraction.model.service.AttractionInfoService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Service
public class AttractionInfoServiceImpl implements AttractionInfoService {
    private final AttractionInfoMapper attractionInfoMapper;

    public AttractionInfoServiceImpl(AttractionInfoMapper attractionInfoMapper) {
        super();
        this.attractionInfoMapper = attractionInfoMapper;
    }

    public List<AttractionInfoDto> selectAttractionList(int sidoCode, int gugunCode) {
        return attractionInfoMapper.selectAttractionList(sidoCode, gugunCode);
    }

    public List<AttractionInfoDto> selectByContentType(int contentTypeId){
        return attractionInfoMapper.selectByContentType(contentTypeId);
    }

    @Override
    public Map<String, Object> getAttractionLst(Map<String, Object> param) {
        return null;
    }
}
