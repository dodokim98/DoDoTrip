package com.ssafy.attraction.model.service.impl;

import com.ssafy.attraction.model.dto.SidoGugunCodeDto;
import com.ssafy.attraction.model.mapper.MapMapper;
import com.ssafy.attraction.model.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MapServiceImpl implements MapService {

    private final MapMapper mapMapper;

    @Override
    public List<SidoGugunCodeDto> getSido() throws Exception {
        return mapMapper.getSido();
    }

    @Override
    public List<SidoGugunCodeDto> getGugunInSido(String sido) throws Exception {
        return mapMapper.getGugunInSido(sido);
    }

    @Override
    public Map<String, Object> getAttractionList(Map<String, Object> paramMap) throws SQLException {
        Map<String, Object> result = new HashMap<>();
        result.put("attractions", mapMapper.selectAttractions(paramMap));

        return result;
    }

}
