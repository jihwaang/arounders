package com.arounders.web.repository.mapper;

import com.arounders.web.dto.ChatRoomDTO;
import com.arounders.web.entity.ChatRoom;
import com.arounders.web.repository.ChatRoomRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatRoomRepositoryImpl implements ChatRoomRepository {

    private final ChatRoomRepository mapper;

    public ChatRoomRepositoryImpl(SqlSession sqlSession){
        this.mapper = sqlSession.getMapper(ChatRoomRepository.class);
    }

    @Override
    public List<ChatRoom> findAllByRegionAndCityId(String region, Integer cityId) {
        return mapper.findAllByRegionAndCityId(region, cityId);
    }

    @Override
    public int insert(ChatRoom chatRoom) {
        return mapper.insert(chatRoom);
    }

    @Override
    public ChatRoomDTO findById(Long id) {
        return mapper.findById(id);
    }

    @Override
    public int close(Long id) {
        return mapper.close(id);
    }

    @Override
    public int update(ChatRoom chatRoom) {
        return mapper.update(chatRoom);
    }
}
