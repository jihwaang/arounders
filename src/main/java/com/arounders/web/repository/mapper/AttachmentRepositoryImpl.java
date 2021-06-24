package com.arounders.web.repository.mapper;

import com.arounders.web.entity.Attachment;
import com.arounders.web.repository.AttachmentRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AttachmentRepositoryImpl implements AttachmentRepository {

    private final AttachmentRepository mapper;

    public AttachmentRepositoryImpl(SqlSession sqlSession) {
        this.mapper = sqlSession.getMapper(AttachmentRepository.class);
    }

    @Override
    public Attachment getAttachment(Long id) {
        return mapper.getAttachment(id);
    }

    @Override
    public int insert(List<Attachment> attachments) {
        return mapper.insert(attachments);
    }

    @Override
    public int delete(Long boardId) {
        return mapper.delete(boardId);
    }

    @Override
    public List<Attachment> findAttachesByBoardId(Long boardId) {
        return mapper.findAttachesByBoardId(boardId);
    }

    @Override
    public Attachment findAttachesByMemberId(Long memberId) {
        return mapper.findAttachesByMemberId(memberId);
    }

    @Override
    public int insertProfileImage(Attachment attachment) {
        return mapper.insertProfileImage(attachment);
    }

    @Override
    public String findProfileImgPathById(Long id) {
        return mapper.findProfileImgPathById(id);
    }
}
