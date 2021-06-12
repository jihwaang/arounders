package com.arounders.web.api;

import com.arounders.web.entity.Report;
import com.arounders.web.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/reports/api/v1")
@Log4j2
@RequiredArgsConstructor
public class ReportApiController {

    private final ReportService service;

    @GetMapping("/{status}")
    public List<Report> getReports(@PathVariable("status") Integer status){

        /* 목록 조회 */
        List<Report> list = service.getReports(status);

        log.info("#ReportApiController : getReports -> ");
        list.forEach(log::info);

        return list;
    }

    @PostMapping("/members/{memberId}/boards/{boardId}")
    public ResponseEntity<Long> report(@PathVariable("memberId") Long memberId,
                                       @PathVariable("boardId") Long boardId){

        log.info(memberId + "번 회원이 " + boardId + "번 게시글을 신고했습니다.");
        Long id = service.register(Report.builder().memberId(memberId).boardId(boardId).build());

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> confirm(@PathVariable("id") Long id){

        log.info("관리자가 " + id + "번째 신고를 처리했습니다.");
        service.finish(id);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
