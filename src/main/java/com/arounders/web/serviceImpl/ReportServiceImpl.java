package com.arounders.web.serviceImpl;

import com.arounders.web.entity.Report;
import com.arounders.web.repository.ReportRepository;
import com.arounders.web.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReportServiceImpl implements ReportService {

    private final ReportRepository repository;

    @Override
    public List<Report> getReports(Integer status) {
        return repository.findAllByStatus(status);
    }

    @Override
    public Long register(Report report) {

        log.info("#ReportService : register -> " + report.getMemberId() + "님이 " + report.getBoardId() + "번 게시글을 신고했습니다.");
        int result = repository.insert(report);

        return result == 1? report.getId() : null;
    }

    @Override
    public Long finish(Long id) {

        log.info("#ReportService : register -> " + id + "번 글의 신고처리가 완료되었습니다.");
        int result = repository.update(id);

        return result == 1? id : null;
    }
}
