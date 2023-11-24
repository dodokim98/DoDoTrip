package com.ssafy.attraction.model.mapper;

import com.ssafy.attraction.model.dto.AttractionInfoDto;
import com.ssafy.attraction.model.dto.SidoGugunCodeDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface MapMapper {

    List<SidoGugunCodeDto> getSido() throws SQLException;

    List<SidoGugunCodeDto> getGugunInSido(String sido) throws SQLException;

    List<AttractionInfoDto> selectAttractions(Map<String, Object> paramMap) throws SQLException;

}
