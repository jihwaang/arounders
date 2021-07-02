package com.arounders.web.repository;

import com.arounders.web.dto.BoardDTO;
import com.arounders.web.dto.criteria.BoardCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class BoardRepositoryTests {

    @Autowired
    BoardRepository repository;

    @Test
    public void getMyboards(){

        BoardCriteria c = new BoardCriteria();
        c.setCityId(1);
        c.setRegion("강북구");
        c.setCategory(1L);

        List<BoardDTO> list = repository.getMyList(c, 12L);

        list.forEach(System.out::println);
    }
}
