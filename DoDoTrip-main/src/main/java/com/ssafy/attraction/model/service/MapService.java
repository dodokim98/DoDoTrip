package com.ssafy.attraction.model.service;

import com.ssafy.attraction.model.dto.SidoGugunCodeDto;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface MapService {

    List<SidoGugunCodeDto> getSido() throws Exception;

    List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception;

    Map<String, Object> getAttractionList(Map<String, Object> paramMap) throws SQLException;

}
