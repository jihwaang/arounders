package com.arounders.web.serviceImpl;

import com.arounders.web.entity.Likes;
import com.arounders.web.repository.LikesRepository;
import com.arounders.web.service.LikesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class LikesServiceImpl implements LikesService {

    private final LikesRepository repository;

    @Override
    public Long like(Likes likes) {

        repository.insert(likes);

        return likes.getBoardId();
    }

    @Override
    public Long cancel(Likes likes) {

        repository.delete(likes);

        return likes.getBoardId();
    }

    @Override
    public int getCount(Long boardId) {

        int count = repository.getCount(boardId);
        log.info("#LikesService : getCount -> " + count + " 글 번호 -> " + boardId);

        return count;
    }
}
