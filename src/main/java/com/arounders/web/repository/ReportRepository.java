package com.arounders.web.repository;

import com.arounders.web.entity.Report;

import java.util.List;

public interface ReportRepository {

    /* 신고 목록 조회*/
    List<Report> findAllByStatus(Integer status);
    /* 신고 등록 */
    int insert(Report report);
    /* 신고 처리 */
    int update(Long id);
}
