package com.arounders.web.repository.mapper;

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
    public List<Report> findAllByStatus(Integer status) {
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
}
