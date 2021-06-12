package com.arounders.web.service;

import com.arounders.web.entity.Report;

import java.util.List;

public interface ReportService {

    /* 신고 목록 조회 */
    List<Report> getReports(Integer status);
    /* 신고 등록 */
    Long register(Report report);
    /* 신고 처리 */
    Long finish(Long id);
}
