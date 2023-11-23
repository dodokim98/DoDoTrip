package com.ssafy.attraction.model.service.impl;

import com.ssafy.attraction.model.dto.PlanAttractionDto;
import com.ssafy.attraction.model.dto.PlanDetailsDto;
import com.ssafy.attraction.model.dto.PlanDto;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssafy.attraction.model.dto.PlanListDto;
import com.ssafy.attraction.model.mapper.PlanMapper;
import com.ssafy.attraction.model.service.PlanService;
import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.board.model.dto.BoardListDto;
import com.ssafy.qna.model.dto.QnaDto;
import com.ssafy.qna.model.dto.QnaListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanMapper planMapper;

    @Override
    public void registPlan(PlanDto planDto) {
        planMapper.insertPlan(planDto);
    }

    @Override
    public PlanDto selectPlan(int planNo) {
        return planMapper.selectPlan(planNo);
    }

    @Override
    public void updatePlan(PlanDto planDto) {
        planMapper.updatePlan(planDto);
    }

    @Override
    public void deletePlan(int planNo) {
        planMapper.deletePlan(planNo);
    }

    @Override
    public List<PlanDto> getPlanListByUser(String userEmail) {
        List<PlanDto> list = planMapper.getPlanListByUser(userEmail);
        return list;
    }

    @Override
    public List<PlanAttractionDto> getPlanAttractionsByPlanNo(int planPlanNo) {
        return planMapper.getPlanAttractionsByPlanNo(planPlanNo);
    }

    @Override
    public List<PlanAttractionDto> getPlanAttraction(int planNo) {
        return planMapper.selectPlanAttractionListByPlanNo(planNo);
    }

    @Override
    public PlanDetailsDto getPlanDetailByPlanNo(int planNo) {
        return planMapper.getPlanDetailByPlanNo(planNo);
    }

    @Override
    public void registPlanAttraction(PlanAttractionDto planAttractionDto) throws SQLException {
        planMapper.insertPlanAttraction(planAttractionDto);
    }

    @Override
    public void modifyPlanAttraction(PlanAttractionDto planAttractionDto) throws SQLException {

    }

}
