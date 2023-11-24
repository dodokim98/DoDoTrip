package com.ssafy.attraction.model.mapper;

import com.ssafy.attraction.model.dto.PlanAttractionDto;
import com.ssafy.attraction.model.dto.PlanDetailsDto;
import com.ssafy.attraction.model.dto.PlanDto;
import com.ssafy.attraction.model.dto.PlanListDto;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface PlanMapper {

    void insertPlan(PlanDto planDto);

    PlanDto selectPlan(int planNo);

    void updatePlan(PlanDto planDto);

    void deletePlan(int planNo);

    List<PlanAttractionDto> selectPlanAttractionListByPlanNo(int planNo);

    PlanDetailsDto getPlanDetailByPlanNo(int planNo);
    List<PlanDto> getPlanListByUser(String userEmail);

    List<PlanAttractionDto> getPlanAttractionsByPlanNo(int planPlanNo);
    int getTotalPlanCount(Map<String, Object> map) throws SQLException;
    /**
     * PlanAttraction
     */
    PlanAttractionDto selectPlanAttraction(int planAttractionNo) throws SQLException;

    void insertPlanAttraction(PlanAttractionDto planAttractionDto) throws SQLException;

    void updatePlanAttraction(PlanAttractionDto planAttractionDto) throws SQLException;

    void deletePlanAttraction(int planAttractionId) throws SQLException;

}
