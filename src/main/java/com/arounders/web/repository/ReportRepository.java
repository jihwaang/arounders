package com.arounders.web.repository;

import com.arounders.web.dto.ReportDTO;
import com.arounders.web.entity.Report;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ReportRepository {

    /* 특정 신고된 글 조회 */
    ReportDTO findById(Long id);
    /* 신고 목록 조회*/
    List<ReportDTO> findAllByStatus(Integer status);
    /* 신고 등록 */
    int insert(Report report);
    /* 신고 처리 */
    int update(Long id);
    /* 이미 신고했는지 여부 */
    Integer isReport(@Param("memberId") Long memberId, @Param("boardId") Long boardId);
}
