package com.ssafy.qna.model.dto;

import com.ssafy.board.model.dto.BoardDto;

import java.util.List;

public class QnaListDto {

	private List<QnaDto> qnas;
	private int currentPage;
	private int totalPageCount;

	public List<QnaDto> getQnas() {
		return qnas;
	}

	public void setQnas(List<QnaDto> qnas) {
		this.qnas = qnas;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPageCount() {
		return totalPageCount;
	}

	public void setTotalPageCount(int totalPageCount) {
		this.totalPageCount = totalPageCount;
	}
}
