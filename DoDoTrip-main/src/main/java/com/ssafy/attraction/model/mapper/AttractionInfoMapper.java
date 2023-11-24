package com.ssafy.attraction.model.mapper;

import com.ssafy.attraction.model.dto.AttractionInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface AttractionInfoMapper {
    List<AttractionInfoDto> selectAttractionList(int sidoCode, int gugunCode);

    List<AttractionInfoDto> selectByContentType(int contentTypeId);
}
