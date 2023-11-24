package com.ssafy.attraction.model.dto;

import com.ssafy.qna.model.dto.QnaDto;
import lombok.Data;

import com.ssafy.board.model.dto.BoardDto;

import java.util.List;

public class PlanListDto {

    private List<PlanDto> plans;
    private int currentPage;
    private int totalPageCount;

    public  List<PlanDto> getPlans() {
        return plans;
    }

    public void setPlans(List<PlanDto> plans) {
        this.plans = plans;
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
