package com.arounders.web.api;

import com.arounders.web.dto.BoardDTO;
import com.arounders.web.dto.criteria.BoardCriteria;
import com.arounders.web.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/boards/api/v1")
@Log4j2
public class BoardApiController {

    private final BoardService service;

    @GetMapping(value = "")
    public ResponseEntity<List<BoardDTO>> getList(BoardCriteria criteria){

        criteria.init();
        List<BoardDTO> list = service.getList(criteria);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
