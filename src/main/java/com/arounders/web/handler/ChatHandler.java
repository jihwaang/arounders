package com.arounders.web.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.server.HandshakeHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.util.ArrayList;
import java.util.List;

@Component
@Log4j2
public class ChatHandler extends TextWebSocketHandler {

    /*
    # 채팅방 1개.
    특정 session에서 서버로 payload(message)를 보내면, 이 채팅방에 접속한 모든 session에게
    payload를 뿌려준다.
    */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();
        log.info("payload : " + payload);
        log.info("message : " + message);

        /*for(WebSocketSession sess : sessionList){
            sess.sendMessage(message);
        }*/

    }

    /* Client 접속 */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        //sessionList.add(session);
        log.info(session.getLocalAddress() + "클라이언트가 접속하셨습니다.");
    }

    /* Client 접속 해제*/
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        log.info(session.getLocalAddress() + "클라이언트가 나가셨습니다.");
        //sessionList.remove((WebSocketSession) session);
    }
}
