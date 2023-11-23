package com.ssafy.attraction.model.mapper;

import com.ssafy.attraction.model.dto.AttractionDescriptionDto;
import com.ssafy.attraction.model.dto.AttractionInfoDto;
import com.ssafy.attraction.model.dto.AttractionLikeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AttractionLikeMapper {
    List<AttractionInfoDto> getAttractionLikeList(String userEmail);

    AttractionDescriptionDto getAttractionDescription(int contentId);

    AttractionLikeDto getAttractionLikeDetail(int likeNo);
    void createAttractionLike(AttractionLikeDto attractionLikeDto);
    void deleteAttractionLike(int likeNo);

    void deleteAttractionLike2(int contentId);

}
