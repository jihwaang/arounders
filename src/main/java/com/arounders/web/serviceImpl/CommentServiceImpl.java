package com.arounders.web.serviceImpl;

import com.arounders.web.dto.CommentDTO;
import com.arounders.web.entity.Comment;
import com.arounders.web.repository.CommentRepository;
import com.arounders.web.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    @Override
    public CommentDTO getComment(Long id) {

        return repository.getComment(id);
    }

    @Override
    public List<CommentDTO> getComments(Long boardId) {

        return repository.getComments(boardId);
    }

    @Override
    public List<CommentDTO> getMyComments(Long id) {
        return repository.getMyComments(id);
    }

    @Override
    public Long create(CommentDTO commentDTO) {

        Comment comment = dtoToEntity(commentDTO);
        int result = repository.insert(comment);

        return result == 1? comment.getId() : null;
    }

    @Override
    public Long update(CommentDTO commentDTO) {

        Comment comment = dtoToEntity(commentDTO);
        int result = repository.update(comment);

        return result == 1? comment.getId() : null;
    }

    @Override
    public Long delete(Long id) {

        int result = repository.delete(id);
        return result > 0? id : null;
    }
}
