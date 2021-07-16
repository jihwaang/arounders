package com.arounders.web.repository.mapper;

import com.arounders.web.dto.ChatRoomDTO;
import com.arounders.web.repository.ChatRepository;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ChatRepositoryImpl implements ChatRepository {

    private final String SEPERATOR = File.separator;

    @Override
    public void save(ChatRoomDTO room, List<String> list, String realPath) {

        //StringBuilder path = new StringBuilder("C:/chat/");
        StringBuilder path = new StringBuilder(realPath);
        path.append(SEPERATOR)
            .append(room.getCityId()).append(SEPERATOR)
            .append(room.getRegion()).append(SEPERATOR)
            .append(room.getId()).append("_").append(room.getTitle()).append(".txt");

        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(path.toString(), true));

            for(String chat : list){
                out.write(chat);
                out.append('\n');
            }
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getChats(ChatRoomDTO room, String realPath) {

        List<String> chats = new ArrayList<>();
        //StringBuilder path = new StringBuilder("C:/chat/");
        StringBuilder path = new StringBuilder(realPath);
        path.append(SEPERATOR)
                .append(room.getCityId()).append(SEPERATOR)
                .append(room.getRegion()).append(SEPERATOR)
                .append(room.getId()).append("_").append(room.getTitle()).append(".txt");

        try(BufferedReader br = new BufferedReader(new FileReader(path.toString()))){

            while(br.ready()) {
                String chat = br.readLine();
                chats.add(chat);
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return chats;
    }
}
