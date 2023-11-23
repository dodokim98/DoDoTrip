package com.ssafy.attraction.controller;


import com.ssafy.attraction.model.dto.PlanAttractionDto;
import com.ssafy.attraction.model.dto.PlanDetailsDto;
import com.ssafy.attraction.model.dto.PlanDto;
import com.ssafy.attraction.model.dto.PlanListDto;
import com.ssafy.attraction.model.service.PlanService;
import com.ssafy.board.model.dto.BoardDto;
import com.ssafy.qna.model.dto.QnaListDto;
import com.ssafy.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"*"}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST}, maxAge = 6000)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Log4j2
public class PlanController {

    private final PlanService planService;
    private final JWTUtil jwtUtil;

    @GetMapping("/plans")
    public ResponseEntity<?> getPlanListByUser(HttpServletRequest req) {
        try {
            String token = req.getHeader("Authorization");
            String userEmail = jwtUtil.getUserEmail(token);
            return new ResponseEntity<List<PlanDto>>(planService.getPlanListByUser(userEmail), HttpStatus.OK);

        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @GetMapping("/plan/{planno}")
    public ResponseEntity<?> getPlanAttractionsByPlanNo(@PathVariable("planno") int planNo) {
        return new ResponseEntity<List<PlanAttractionDto>>(planService.getPlanAttractionsByPlanNo(planNo), HttpStatus.OK);

    }


//    @GetMapping("/plans")
//    public Map<String, Object> getPlanList(@RequestParam Map<String, Object> map) {
//        return planService.getPlanList(map);
//    }

//    @GetMapping("/plan/{planno}")
//    public PlanDto getPlan(@PathVariable("planno") int planNo) {
//        return planService.selectPlan(planNo);
//    }

    @PostMapping("/plan")
    public ResponseEntity<PlanDto> registPlan(@RequestBody PlanDto planDto, HttpServletRequest req) {
        try {
            String email = jwtUtil.getUserEmail(req.getHeader("Authorization"));
            planDto.setMemberUserEmail(email);
            planService.registPlan(planDto);
            PlanDto createdPlan = planService.selectPlan(planDto.getPlanNo());
            return new ResponseEntity<PlanDto>(createdPlan, HttpStatus.CREATED);

        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    @PutMapping("/plan")
    public void updatePlan(@RequestBody PlanDto planDto) {
        planService.updatePlan(planDto);
    }

    @DeleteMapping("/plan/{planno}")
    public void deletePlan(@PathVariable("planno") int planNo) {
        planService.deletePlan(planNo);
    }

    /**
     * Plan Attraction
     */
    @PostMapping("/{planno}/attraction")
    public ResponseEntity<?> createPlanAttraction(@PathVariable("planno") int planNo, @RequestBody PlanAttractionDto planAttractionDto) throws SQLException {
        try {
            planAttractionDto.setPlanPlanNo(planNo);
            planService.registPlanAttraction(planAttractionDto);
            return new ResponseEntity<Void>(HttpStatus.CREATED);

        } catch (Exception e) {
            return exceptionHandling(e);
        }
    }

    private ResponseEntity<PlanDto> exceptionHandling(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
