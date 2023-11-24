package com.ssafy.attraction.model.service.impl;

import com.ssafy.attraction.model.dto.AttractionDescriptionDto;
import com.ssafy.attraction.model.dto.AttractionInfoDto;
import com.ssafy.attraction.model.dto.AttractionLikeDto;
import com.ssafy.attraction.model.mapper.AttractionLikeMapper;
import com.ssafy.attraction.model.service.AttractionLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AttractionLikeServiceImpl implements AttractionLikeService {
    private final AttractionLikeMapper attractionLikeMapper;

    @Override
    public List<AttractionInfoDto> getAttractionLikeList(String userEmail) {
        return attractionLikeMapper.getAttractionLikeList(userEmail);
    }

    @Override
    public AttractionDescriptionDto getAttractionDescription(int contentId) {
        return attractionLikeMapper.getAttractionDescription(contentId);
    }

    @Override
    public AttractionLikeDto getAttractionLikeDetail(int likeNo) {
        return attractionLikeMapper.getAttractionLikeDetail(likeNo);
    }
    @Override
    public void createAttractionLike(AttractionLikeDto attractionLikeDto) {
        attractionLikeMapper.createAttractionLike(attractionLikeDto);
    }
    @Override
    public void deleteAttractionLike(int likeNo) {
        attractionLikeMapper.deleteAttractionLike(likeNo);
    }

    @Override
    public void deleteAttractionLike2(int contentId) {
        attractionLikeMapper.deleteAttractionLike2(contentId);
    }


}
