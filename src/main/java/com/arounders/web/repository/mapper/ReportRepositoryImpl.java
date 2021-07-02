package com.arounders.web.repository.mapper;

import com.arounders.web.dto.ReportDTO;
import com.arounders.web.entity.Report;
import com.arounders.web.repository.ReportRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReportRepositoryImpl implements ReportRepository{

    private final ReportRepository repository;

    public ReportRepositoryImpl(SqlSession sqlSession){
        this.repository = sqlSession.getMapper(ReportRepository.class);
    }

    @Override
    public ReportDTO findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<ReportDTO> findAllByStatus(Integer status) {
        return repository.findAllByStatus(status);
    }

    @Override
    public int insert(Report report) {
        return repository.insert(report);
    }

    @Override
    public int update(Long id) {
        return repository.update(id);
    }

    @Override
    public Integer isReport(Long memberId, Long boardId) {
        return repository.isReport(memberId, boardId);
    }
}
