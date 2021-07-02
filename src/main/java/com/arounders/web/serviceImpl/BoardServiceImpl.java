package com.arounders.web.serviceImpl;

import com.arounders.web.dto.BoardDTO;
import com.arounders.web.dto.criteria.BoardCriteria;
import com.arounders.web.entity.Attachment;
import com.arounders.web.entity.Board;
import com.arounders.web.entity.Category;
import com.arounders.web.repository.BoardRepository;
import com.arounders.web.service.AttachmentService;
import com.arounders.web.service.BoardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

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
    public List<BoardDTO> getList(Long memberId, BoardCriteria criteria) {
        return boardRepository.getList(memberId, criteria);
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

    /* About Mypage */
    @Override
    public List<BoardDTO> getMyList(BoardCriteria criteria, Long memberId) {
        return boardRepository.getMyList(criteria, memberId);
    }

    @Override
    public Map<String, Integer> getCountListByCategory(Long memberId) {

        Map<String, Integer> countMap = new LinkedHashMap<>();

        Arrays.stream(Category.values())
                .forEach(c -> countMap
                        .put(c.getTitle()
                                ,boardRepository.getCountByCategory(memberId, c.getId())));

        return countMap;
    }

    /* 게시글 숨김 */
    @Override
    public Long hideBoard(Long boardId) {

        int result = boardRepository.hide(boardId);

        return result > 0? boardId : null;
    }
    /* 게시글 숨김 해제 */
    @Override
    public Long showBoard(Long boardId) {

        int result = boardRepository.show(boardId);

        return result > 0? boardId : null;
    }

    @Override
    public Long done(Long boardId) {

        int result = boardRepository.done(boardId);

        return result > 0? boardId : null;
    }
}
