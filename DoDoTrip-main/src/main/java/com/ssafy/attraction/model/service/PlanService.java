package com.ssafy.attraction.model.service;

import com.ssafy.attraction.model.dto.PlanAttractionDto;
import com.ssafy.attraction.model.dto.PlanDetailsDto;
import com.ssafy.attraction.model.dto.PlanDto;
import com.ssafy.attraction.model.dto.PlanListDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface PlanService {


    void registPlan(PlanDto planDto);

    PlanDto selectPlan(int planNo);

    void updatePlan(PlanDto planDto);

    void deletePlan(int planNo);

    List<PlanDto> getPlanListByUser(String userEmail);

    List<PlanAttractionDto> getPlanAttractionsByPlanNo(int planPlanNo);

    /**
     * PlanAttraction
     */
    List<PlanAttractionDto> getPlanAttraction(int planNo);

    PlanDetailsDto getPlanDetailByPlanNo(int planNo);

    void registPlanAttraction(PlanAttractionDto planAttractionDto) throws SQLException;

    void modifyPlanAttraction(PlanAttractionDto planAttractionDto) throws SQLException;

}
