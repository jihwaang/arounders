package com.arounders.web.config;

import com.arounders.web.handler.ChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@RequiredArgsConstructor
@EnableWebSocket //웹소켓 활성화에 필요한 어노테이션
public class WebSocketConfig implements WebSocketConfigurer {

    private final ChatHandler handler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {

        webSocketHandlerRegistry
                .addHandler(handler, "ws/chat")
                .setAllowedOriginPatterns("http://*:8080", "http://*.*.*.*:8080")
                .withSockJS()
                .setClientLibraryUrl("https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/1.1.2/sockjs.js");
    }
}
