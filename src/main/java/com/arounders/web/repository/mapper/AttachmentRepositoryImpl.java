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
    public List<Attachment> getList() {
        return mapper.getList();
    }

    @Override
    public Attachment getAttachment(Integer id) {
        return mapper.getAttachment(id);
    }

    @Override
    public Attachment findThumbInfo(Long boardId, Long memberId, Integer index) {
        return mapper.findThumbInfo(boardId, memberId, index);
    }

    @Override
    public int insert(Attachment attachment) {
        return mapper.insert(attachment);
    }

    @Override
    public int update(Attachment attachment) {
        return mapper.update(attachment);
    }

    @Override
    public int delete(Integer id) {
        return mapper.delete(id);
    }

}
