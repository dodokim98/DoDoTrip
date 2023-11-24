package com.ssafy.attraction.controller;

import com.ssafy.attraction.model.dto.AttractionDescriptionDto;
import com.ssafy.attraction.model.dto.AttractionInfoDto;
import com.ssafy.attraction.model.service.AttractionLikeService;
import org.springframework.web.bind.annotation.*;

import com.ssafy.attraction.model.dto.AttractionLikeDto;

import java.util.List;

@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/api")
public class AttractionLikeController {

	private final AttractionLikeService attractionLikeService;

	public AttractionLikeController(AttractionLikeService attractionLikeService) {
		this.attractionLikeService = attractionLikeService;
	}

	@GetMapping(value = "/attractions/like/{userEmail}")
	public List<AttractionInfoDto> getAttractionLikeList(@PathVariable String userEmail){
		return attractionLikeService.getAttractionLikeList(userEmail);
	}

	@GetMapping(value = "/attractions/{likeNo}")
	public AttractionLikeDto getAttractionLikeDetail(@PathVariable("likeNo") int likeNo) {
		return attractionLikeService.getAttractionLikeDetail(likeNo);
	}

	@GetMapping(value = "/attractions/detail/{contentId}")
	public AttractionDescriptionDto getAttractionDescription(@PathVariable("contentId") int contentId){
		return attractionLikeService.getAttractionDescription(contentId);
	}

	@PostMapping(value = "/attractions/create")
	public void createAttractionLike(@RequestBody AttractionLikeDto attractionLikeDto) {
		attractionLikeService.createAttractionLike(attractionLikeDto);
	}

	@DeleteMapping(value = "/attractions/{likeNo}")
	public void deleteAttractionLike(@PathVariable("likeNo") int likeNo) {
		attractionLikeService.deleteAttractionLike(likeNo);
	}

	@DeleteMapping(value = "/attractions/like/{contentId}")
	public void deleteAttractionLike2(@PathVariable("contentId") int contentId) {
		attractionLikeService.deleteAttractionLike2(contentId);
	}

}
