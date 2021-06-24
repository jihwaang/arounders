package com.arounders.web.serviceImpl;

import com.arounders.web.dto.BoardDTO;
import com.arounders.web.entity.Attachment;
import com.arounders.web.entity.Member;
import com.arounders.web.repository.AttachmentRepository;
import com.arounders.web.service.AttachmentService;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public Attachment getAttachment(Long id) {
        return attachmentRepository.getAttachment(id);
    }

    @Override
    public int createAttachment(List<Attachment> attachments) {
        return attachmentRepository.insert(attachments);
    }

    @Override
    public int removeAttachment(Long boardId) {
        return attachmentRepository.delete(boardId);
    }

    @Override
    public List<Attachment> getBoardAttachments(Long boardId) {
        return attachmentRepository.findAttachesByBoardId(boardId);
    }

    @Override
    public Attachment getMemberProfile(Long memberId) {
        return attachmentRepository.findAttachesByMemberId(memberId);
    }

    /* 파일로 저장 */
    @Override
    public void save(MultipartFile file, Attachment attachment, String realPath) {

        String savePath = realPath + File.separator + attachment.getPath();
        String saveFileName = attachment.getId() + "_" + attachment.getName();

        File saveFile = new File(savePath, saveFileName);

        try {
            file.transferTo(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*/upload 하위 폴더 경로 생성 */
    public String createFilePath(String realPath) {
        LocalDateTime date = LocalDateTime.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

        String result = formatter.format(date);

        createFolders(result, realPath);

        return result;
    }

    private void createFolders(String paths, String realPath) {

        File folders = new File(realPath, paths);

        if(!folders.exists())
            folders.mkdirs();
    }

    @Override
    public List<Attachment> attachmentsProcess(MultipartFile[] postFiles, BoardDTO boardDTO, String realPath, Long memberId, Integer thumbIdx) {

        List<Attachment> attachments = null;

        if(postFiles[0].getSize() > 0) {
            String uploadPath = createFilePath(realPath);
            attachments = new ArrayList<>();

            /* Multipart -> Attachment */
            for(MultipartFile file : postFiles){

                String uuid = UUID.randomUUID().toString();
                String fileName = file.getOriginalFilename();

                Attachment attachment = Attachment.builder()
                        .id(uuid)
                        .name(fileName)
                        .path(uploadPath)
                        .memberId(memberId)
                        .build();

                attachments.add(attachment);
                /* 파일 저장 */
                save(file, attachment, realPath);
            }

            /* 썸네일 정보 저장 */
            Attachment thumbnail = attachments.get(thumbIdx);
            String thumbnailName = "s_" + thumbnail.getId() + "_" + thumbnail.getName();
            String path = realPath + File.separator + uploadPath;

            boardDTO.setThumbnailName(thumbnailName);
            boardDTO.setThumbnailPath(thumbnail.getPath());
            /* 썸네일 파일 생성 후 저장 */
            try {
                Thumbnailator.createThumbnail(
                        new File(path, thumbnail.getId() + "_" + thumbnail.getName()),
                        new File(path, thumbnailName),
                        100, 100);
            } catch (IOException e) {
                e.printStackTrace();
            }
            /* 로컬 저장소 파일 저장, BoardDTO에 Thumbnail 정보가 심어짐, Attachment List OK */
        }

        return attachments;
    }

    @Transactional
    @Override
    public int saveOneFile(MultipartFile multipartFile, String realPath, Member member) {

        String uuid = UUID.randomUUID().toString();
        String fileName = multipartFile.getOriginalFilename();
        String uploadPath = createFilePath(realPath);

        Attachment attachment = Attachment.builder()
                                    .id(uuid)
                                    .name(fileName)
                                    .path(uploadPath)
                                    .memberId(member.getId())
                                    .build();
        /* save file here */
        save(multipartFile, attachment, realPath);

        return createOneAttachment(attachment);
    }

    @Override
    public int createOneAttachment(Attachment attachment) {
        return attachmentRepository.insertProfileImage(attachment);
    }

    @Override
    public String findProfileImgPathById(Long id) {
        return attachmentRepository.findProfileImgPathById(id);
    }
}
