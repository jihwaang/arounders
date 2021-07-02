package com.arounders.web.repository.mapper;

import com.arounders.web.dto.ChatMemberDTO;
import com.arounders.web.dto.ChatRoomDTO;
import com.arounders.web.entity.Member;
import com.arounders.web.repository.ChatMemberRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatMemberRepositoryImpl implements ChatMemberRepository{

    private final ChatMemberRepository mapper;

    public ChatMemberRepositoryImpl(SqlSession sqlSession){
        this.mapper = sqlSession.getMapper(ChatMemberRepository.class);
    }

    @Override
    public int insert(Long memberId, Long chatRoomId) {
        return mapper.insert(memberId, chatRoomId);
    }

    @Override
    public int delete(Long memberId, Long chatRoomId) {
        return mapper.delete(memberId, chatRoomId);
    }

    @Override
    public int isJoin(Long memberId, Long chatRoomId) {
        return mapper.isJoin(memberId, chatRoomId);
    }

    @Override
    public List<ChatRoomDTO> findAllByMemberId(Long memberId) {
        return mapper.findAllByMemberId(memberId);
    }

    @Override
    public List<ChatMemberDTO> findByChatRoomId(Long chatRoomId) {
        return mapper.findByChatRoomId(chatRoomId);
    }
}
