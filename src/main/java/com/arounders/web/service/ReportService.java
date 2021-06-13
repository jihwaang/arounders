package com.arounders.web.service;

import com.arounders.web.dto.ReportDTO;
import com.arounders.web.entity.Report;

import java.util.List;

public interface ReportService {

    /* 특정 신고된 글 조회 */
    ReportDTO getReport(Long id);
    /* 신고 목록 조회 */
    List<ReportDTO> getReports(Integer status);
    /* 신고 등록 */
    Long register(Report report);
    /* 신고 처리 */
    Long finish(Long id);
}
