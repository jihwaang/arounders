package com.arounders.web.serviceImpl;

import com.arounders.web.dto.BoardDTO;
import com.arounders.web.entity.Attachment;
import com.arounders.web.entity.Board;
import com.arounders.web.repository.BoardRepository;
import com.arounders.web.service.AttachmentService;
import com.arounders.web.service.BoardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;


    public BoardServiceImpl(BoardRepository boardRepository, AttachmentService attachmentService) {
        this.boardRepository = boardRepository;
    }

    @Override
    public List<BoardDTO> getHiddenList() {
        return boardRepository.getHiddenList();
    }

    @Override
    public List<BoardDTO> getList() {
        return boardRepository.getList();
    }

    @Override
    public BoardDTO getBoard(Long id) {
        return boardRepository.getBoard(id);
    }

    @Override
    public Long createBoard(BoardDTO boardDTO) {

        Board board = dtoToEntity(boardDTO);

        int result = boardRepository.insert(board);

        return result > 0? board.getId() : null;
    }

    @Override
    public Long editBoard(BoardDTO boardDTO) {

        Board board = dtoToEntity(boardDTO);
        int result = boardRepository.update(board);

        return result > 0? board.getId() : null;
    }

    @Override
    public Long removeBoard(Long id) {

        int result = boardRepository.delete(id);

        return result > 0? id : null;
    }

/*    private MultipartFile[] addThumbNail(MultipartFile[] postFiles, Integer thumbIdx) {

        MultipartFile[] postFilesWithThumbNail = new MultipartFile[postFiles.length+1];
        for (int i = 0 ; i < postFilesWithThumbNail.length; i++) {
            if (i == postFilesWithThumbNail.length-1)
                postFilesWithThumbNail[i] = postFiles[thumbIdx];
            else
                postFilesWithThumbNail[i] = postFiles[i];
        }
        return postFilesWithThumbNail;

    }*/
}
