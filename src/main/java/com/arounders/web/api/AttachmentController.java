package com.arounders.web.api;

import com.arounders.web.entity.Attachment;
import com.arounders.web.entity.Board;
import com.arounders.web.service.AttachmentService;
import com.arounders.web.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attachment")
@Slf4j
public class AttachmentController {

    private final AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }
/*

    @GetMapping("/getList")
    public List<Attachment> getList(Model model) {
        List<Attachment> list = attachmentService.getList();
        model.addAttribute("list", list);
        return list;
    }

    @GetMapping("/getAttachment/{id}")
    public Attachment getAttachment(Model model, @PathVariable Integer id) {
        log.info("id : {}", id);
        Attachment attachment = attachmentService.getAttachment(id);
        model.addAttribute("attachment", attachment);
        return attachment;
    }

    @PostMapping("/createAttachment")
    public int createAttachment(Model model, @RequestBody Attachment attachment) {
        log.info("request attachment : {}", attachment);
        int result = attachmentService.createAttachment(attachment);
        log.info("generated id : {}", attachment.getId());
        model.addAttribute("id", attachment.getId());
        return result;
    }

    @PutMapping("/editAttachment") // not using
    public int editAttachment(Model model, @RequestBody Attachment attachment) {
        log.info("request attachment : {}", attachment);
        int result = attachmentService.editAttachment(attachment);
        model.addAttribute("id", attachment.getId());
        return result;
    }

    @DeleteMapping("/removeAttachment/{id}")
    public int removeAttachment(Model model, @PathVariable Integer id) {
        log.info("request id : {}", id);
        int result = attachmentService.removeAttachment(id);
        return result;
    }
*/

}
