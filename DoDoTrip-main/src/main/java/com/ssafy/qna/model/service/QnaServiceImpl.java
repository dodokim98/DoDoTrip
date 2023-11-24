package com.ssafy.qna.model.service;

import com.ssafy.qna.model.dto.QnaDto;
import com.ssafy.qna.model.dto.QnaListDto;
import com.ssafy.qna.model.mapper.QnaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService{

    private final QnaMapper qnaMapper;

    @Override
    public void registerQna(QnaDto qnaDto) throws Exception {
        qnaMapper.registerQna(qnaDto);
    }

    @Override
    public void registerAnswer(QnaDto qnaDto) throws Exception {
        qnaMapper.registerAnswer(qnaDto);
    }

    @Override
    public QnaListDto getQnaList(Map<String, String> map) throws Exception {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("word", map.get("word") == null ? "" : map.get("word"));
        int currentPage = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
        int sizePerPage = Integer.parseInt(map.get("spp") == null ? "10" : map.get("spp"));
        int start = currentPage * sizePerPage - sizePerPage;
        param.put("start", start);
        param.put("listsize", sizePerPage);

        List<QnaDto> list = qnaMapper.getQnaList(param);

        int totalArticleCount = qnaMapper.getTotalQnaCount(param);
        int totalPageCount = (totalArticleCount - 1) / sizePerPage + 1;

        QnaListDto qnaListDto = new QnaListDto();
        qnaListDto.setQnas(list);
        qnaListDto.setCurrentPage(currentPage);
        qnaListDto.setTotalPageCount(totalPageCount);

        return qnaListDto;
    }

    @Override
    public QnaDto getQnaDetail(int qnaNo) throws Exception {
        return qnaMapper.getQnaDetail(qnaNo);
    }

    @Override
    public void deleteQna(int qnaNo) throws Exception {
        qnaMapper.deleteQna(qnaNo);
    }
}
