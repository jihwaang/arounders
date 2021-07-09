package com.arounders.web.repository;

import com.arounders.web.dto.ReportDTO;
import com.arounders.web.entity.Report;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReportRepositoryTest {

    @Autowired private ReportRepository repository;

    @Test
    void findAllByStatus() {

        //given
        int page = 1;
        int limit = 20;
        int offset = (page - 1) * limit;

        Long boardId = null;
        Integer status = null;

        //when
        List<ReportDTO> list = repository.findAllByOptions(status, boardId, limit, offset);
        list.forEach(System.out::println);

        //then
        Assertions.assertThat(list.size()).isEqualTo(5);
    }

    @Test
    void insert() {

        Report report = Report.builder().memberId(4L).boardId(12L).build();

        int result = repository.insert(report);

        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    void update() {

        Long id = 1L;

        int result = repository.update(id);

        Assertions.assertThat(result).isEqualTo(1);
    }
}