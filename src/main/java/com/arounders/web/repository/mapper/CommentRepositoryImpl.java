package com.arounders.web.repository.mapper;

import com.arounders.web.dto.CommentDTO;
import com.arounders.web.dto.criteria.CommentCriteria;
import com.arounders.web.entity.Comment;
import com.arounders.web.repository.CommentRepository;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentRepository repository;

    public CommentRepositoryImpl(SqlSession sqlSession) {
        this.repository = sqlSession.getMapper(CommentRepository.class);
    }

    @Override
    public CommentDTO getComment(Long id) {
        return repository.getComment(id);
    }

    @Override
    public List<CommentDTO> getComments(Long boardId, CommentCriteria criteria) {
        return repository.getComments(boardId, criteria);
    }

    @Override
    public List<CommentDTO> getReComments(Long boardId, Long upperId) {
        return repository.getReComments(boardId, upperId);
    }

    @Override
    public List<CommentDTO> getMyComments(Long memberId, @Param("cri") CommentCriteria criteria) {
        return repository.getMyComments(memberId, criteria);
    }

    @Override
    public int insert(Comment comment) {
        return repository.insert(comment);
    }

    @Override
    public int update(Comment comment) {
        return repository.update(comment);
    }

    @Override
    public int delete(Long id) {
        return repository.delete(id);
    }

    @Override
    public int getCount(Long boardId) {
        return repository.getCount(boardId);
    }
}
