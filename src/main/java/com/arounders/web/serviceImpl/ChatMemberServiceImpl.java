package com.arounders.web.serviceImpl;

import com.arounders.web.dto.ChatRoomDTO;
import com.arounders.web.repository.ChatMemberRepository;
import com.arounders.web.service.ChatMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMemberServiceImpl implements ChatMemberService {

    private final ChatMemberRepository repository;

    @Override
    public Long join(Long memberId, Long chatRoomId) {

        int result = repository.insert(memberId, chatRoomId);

        return result > 0? chatRoomId : null;
    }

    @Override
    public Long dropOut(Long memberId, Long chatRoomId) {

        int result = repository.delete(memberId, chatRoomId);

        return result > 0? chatRoomId : null;
    }

    @Override
    public int isJoin(Long memberId, Long chatRoomId) {
        return repository.isJoin(memberId, chatRoomId);
    }

    @Override
    public List<ChatRoomDTO> getMyChatRooms(Long memberId) {
        return repository.findAllByMemberId(memberId);
    }
}
