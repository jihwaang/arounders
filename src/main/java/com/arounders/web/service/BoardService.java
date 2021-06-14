package com.arounders.web.service;

import com.arounders.web.entity.Board;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

public interface BoardService {

    List<Board> getList();

    Board getBoard(Integer id);

    int createBoard(Board board, MultipartFile[] postFiles, String uploadPath, Integer thumbIdx);

    int editBoard(Board board);

    int removeBoard(Integer id);


}
