package com.arounders.web.repository.mapper;

import com.arounders.web.entity.Comment;
import com.arounders.web.repository.CommentRepository;
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
    public Comment getComment(Long id) {
        return repository.getComment(id);
    }

    @Override
    public List<Comment> getComments(Long boardId) {
        return repository.getComments(boardId);
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
}
