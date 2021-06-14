package com.arounders.web.serviceImpl;

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

    private final AttachmentService attachmentService;

    public BoardServiceImpl(BoardRepository boardRepository, AttachmentService attachmentService) {
        this.boardRepository = boardRepository;
        this.attachmentService = attachmentService;
    }

    @Override
    public List<Board> getList() {
        return boardRepository.getList();
    }

    @Override
    public Board getBoard(Integer id) {
        return boardRepository.getBoard(id);
    }

    @Transactional
    @Override
    public int createBoard(Board board, MultipartFile[] postFiles, String uploadPath, Integer thumbIdx) {

        if (board == null) return -1; // null check on board

        int result = -1;

        result = boardRepository.insert(board); // generate board
        Long generatedBoardId = board.getId();
        Long memberId = board.getMemberId();

        // #1 add thumbnail img if existing
        //if (thumbIdx != null && thumbIdx < postFiles.length) postFiles = addThumbNail(postFiles, thumbIdx);


        // upload images if existing
        if (postFiles != null) result = attachmentService.uploadFiles(postFiles, uploadPath, generatedBoardId, memberId);
        if (thumbIdx != null) {
            Attachment thumbnailInfo = attachmentService.findThumbInfo(generatedBoardId, memberId, thumbIdx);
            result = boardRepository.renew(thumbnailInfo.getName(), thumbnailInfo.getPath(), thumbnailInfo.getBoardId());
        }
        return result;

    }

    @Override
    public int editBoard(Board board) {
        return boardRepository.update(board);
    }

    @Override
    public int removeBoard(Integer id) {
        return boardRepository.delete(id);
    }

    private MultipartFile[] addThumbNail(MultipartFile[] postFiles, Integer thumbIdx) {

        MultipartFile[] postFilesWithThumbNail = new MultipartFile[postFiles.length+1];
        for (int i = 0 ; i < postFilesWithThumbNail.length; i++) {
            if (i == postFilesWithThumbNail.length-1)
                postFilesWithThumbNail[i] = postFiles[thumbIdx];
            else
                postFilesWithThumbNail[i] = postFiles[i];
        }
        return postFilesWithThumbNail;

    }
}
