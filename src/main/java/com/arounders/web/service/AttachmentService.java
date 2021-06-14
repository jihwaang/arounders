package com.arounders.web.service;

import com.arounders.web.entity.Attachment;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AttachmentService {
    List<Attachment> getList();

    Attachment getAttachment(Integer id);

    int createAttachment(Attachment attachment);

    int editAttachment(Attachment attachment);

    int removeAttachment(Integer id);

    int uploadFiles(MultipartFile[] postFiles, String uploadPath, Long boardId, Long memberId);

    Attachment findThumbInfo(Long generatedBoardId, Long memberId, Integer thumbIdx);
}
