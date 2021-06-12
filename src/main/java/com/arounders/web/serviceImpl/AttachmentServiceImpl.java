package com.arounders.web.serviceImpl;

import com.arounders.web.entity.Attachment;
import com.arounders.web.repository.AttachmentRepository;
import com.arounders.web.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public List<Attachment> getList() {
        return attachmentRepository.getList();
    }

    @Override
    public Attachment getAttachment(Integer id) {
        return attachmentRepository.getAttachment(id);
    }

    @Override
    public int createAttachment(Attachment attachment) {
        return attachmentRepository.insert(attachment);
    }

    @Override
    public int editAttachment(Attachment attachment) {
        return attachmentRepository.update(attachment);
    }

    @Override
    public int removeAttachment(Integer id) {
        return attachmentRepository.delete(id);
    }

    @Override
    public int uploadFiles(MultipartFile[] requestFiles, String uploadPath, Long boardId, Long memberId) {

        int idx = -1;
        int result = -1;

        String realPath = confirmDir(uploadPath);

        for (MultipartFile file : requestFiles) {

            if (file.getSize() == 0) continue;
            String uuid = UUID.randomUUID().toString();
            String fileName = String.valueOf(++idx) + "_" + uuid + "_" + file.getOriginalFilename();

            try {
                file.transferTo(new File(realPath, fileName));

                result = createAttachment(
                        Attachment.builder()
                                      .id(uuid)
                                      .name(fileName)
                                      .path(realPath)
                                      .boardId(boardId)
                                      .memberId(memberId)
                                      .build()
                                );

            } catch (IOException e) {
                e.printStackTrace();
                log.info("file error info: {}", file);
                return result;
            }

        }
        return result;
    }

    @Override
    public Attachment findThumbInfo(Long generatedBoardId, Long memberId, Integer thumbIdx) {
      return attachmentRepository.findThumbInfo(generatedBoardId, memberId, thumbIdx);
    };


    private String confirmDir(String uploadPath) {

        LocalDate date = LocalDate.now();
        String year = String.valueOf(date.getYear());
        String month = String.valueOf(date.getMonthValue());
        String day = String.valueOf(date.getDayOfMonth());

        String[] dateDir = { year, month, day };

        StringBuffer path = new StringBuffer(uploadPath);

        for (int i = 0; i <= dateDir.length; i++) {
            File uploadDir = new File(path.toString());
            if (!uploadDir.exists()) uploadDir.mkdirs();
            if (i < dateDir.length) path.append(File.separator+dateDir[i]);
        }

        return path.toString();

    }


}
