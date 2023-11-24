package com.ssafy.attraction.model.service;

import com.ssafy.attraction.model.dto.AttractionDescriptionDto;
import com.ssafy.attraction.model.dto.AttractionInfoDto;
import com.ssafy.attraction.model.dto.AttractionLikeDto;

import java.util.List;

public interface AttractionLikeService {
    List<AttractionInfoDto> getAttractionLikeList(String userEmail);

    AttractionDescriptionDto getAttractionDescription(int contentId);

    AttractionLikeDto getAttractionLikeDetail(int likeNo);

    void createAttractionLike(AttractionLikeDto attractionLikeDto);

    void deleteAttractionLike(int likeNo);

    void deleteAttractionLike2(int contentId);
}
