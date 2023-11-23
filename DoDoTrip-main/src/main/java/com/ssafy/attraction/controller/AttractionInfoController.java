package com.ssafy.attraction.controller;

import com.ssafy.attraction.model.dto.AttractionInfoDto;
import com.ssafy.attraction.model.mapper.AttractionInfoMapper;
import com.ssafy.attraction.model.service.AttractionInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AttractionInfoController {

	private final AttractionInfoService attractionInfoService;

//	@GetMapping("/attractions/{contentTypeId}")
//	public List<AttractionInfoDto> selectByContentType(@PathVariable int contentTypeId) {
//		return attractionInfoService.selectByContentType(contentTypeId);
//	}
//
//	@GetMapping("/attractions/gugunList")
//	public List<AttractionInfoDto> gugunList() {
//
//	}

//	@GetMapping("/{attractionId}")
//	public Attraction getAttraction(@PathVariable int attractionId) throws SQLException {
//		System.out.println(attractionId);
//		return attractionService.getAttraction(attractionId);
//	}

	@GetMapping("/attractions")
	public Map<String, Object> getAttractionList(@RequestParam Map<String, Object> param) {
		return attractionInfoService.getAttractionLst(param);
	}
}
