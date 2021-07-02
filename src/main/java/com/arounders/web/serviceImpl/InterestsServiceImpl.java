package com.arounders.web.serviceImpl;

import com.arounders.web.entity.Interests;
import com.arounders.web.repository.InterestsRepository;
import com.arounders.web.service.InterestsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class InterestsServiceImpl implements InterestsService {

    private final InterestsRepository interestsRepository;

    public InterestsServiceImpl(InterestsRepository interestsRepository) {
        this.interestsRepository = interestsRepository;
    }

    @Override
    public int getCountOfMember(Long memberId) {
        return interestsRepository.getCountOfMember(memberId);
    }

    @Override
    public int getCountOfBoard(Long boardId) {
        return interestsRepository.getCountOfBoard(boardId);
    }

    @Override
    public int toggleInterests(Interests interests) {
        int exsistingNumber = interestsRepository.getExsistingCount(interests);
        log.info("exsistingNumber: {}", exsistingNumber);
        return exsistingNumber == 0 ? interestsRepository.insert(interests) : interestsRepository.delete(interests);
    }

    @Override // not using
    public int cancelInterests(Interests interests) {
        return interestsRepository.delete(interests);
    }
}
