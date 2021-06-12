package com.arounders.web.repository;

import com.arounders.web.dto.CommentDTO;
import com.arounders.web.entity.Comment;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CommentRepositoryTest {

    @Autowired private CommentRepository repository;

    @Test
    void getComment() {

        Long id = 11L;

        CommentDTO comment = repository.getComment(id);
        System.out.println("comment = " + comment);
        Assertions.assertThat(comment.getId()).isEqualTo(id);
    }

    @Test
    void getComments() {

        Long boardId = 6L;

        List<CommentDTO> comments = repository.getComments(boardId);

        Assertions.assertThat(comments.size()).isEqualTo(2);
    }

    @Test
    void insert() {

        String content = "Test Comment!";
        Long memberId = 12L;
        Long boardId = 6L;

        Comment comment = Comment.builder().content(content).memberId(memberId).boardId(boardId).build();
        repository.insert(comment);

        System.out.println(comment.getId());

        Assertions.assertThat(comment.getId()).isNotEqualTo(null);
    }
/*
    @Test
    void update() {

        Comment comment = repository.getComment(11L);

        comment.setContent("updated!");
        repository.update(comment);
        System.out.println("comment = " + comment);

        Assertions.assertThat(comment.getContent()).isEqualTo("updated!");
    }

    @Test
    void delete() {

        Long id = 11L;

        repository.delete(id);
        Comment comment = repository.getComment(id);

        Assertions.assertThat(comment).isEqualTo(null);
    }
    */
}