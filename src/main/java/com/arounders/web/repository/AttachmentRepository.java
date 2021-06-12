package com.arounders.web.repository;

import com.arounders.web.entity.Attachment;

import java.util.List;

public interface AttachmentRepository {
    List<Attachment> getList();

    Attachment getAttachment(Integer id);

    Attachment findThumbInfo(Long boardId, Long memberId, Integer thumbIdx);

    int insert(Attachment attachment);

    int update(Attachment attachment);

    int delete(Integer id);
}
