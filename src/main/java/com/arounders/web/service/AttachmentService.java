package com.arounders.web.service;

import com.arounders.web.dto.BoardDTO;
import com.arounders.web.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {

    Attachment getAttachment(Long id);

    int createAttachment(List<Attachment> attachments);

    int removeAttachment(Long id);

    void save(MultipartFile file, Attachment attachment, String realPath);

    List<Attachment> getBoardAttachments(Long boardId);

    Attachment getMemberProfile(Long memberId);

    List<Attachment> attachmentsProcess(MultipartFile[] postFiles, BoardDTO boardDTO, String realPath, Long memberId, Integer thumbIdx);
}
