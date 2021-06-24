package com.arounders.web.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Collections;
import java.util.EventListener;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@RequiredArgsConstructor
@EnableWebSocketMessageBroker //웹소켓 활성화에 필요한 어노테이션
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("stomp/chat")
                .setAllowedOriginPatterns("http://*:8080", "http://*.*.*.*:8080")
                .withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        registry.setApplicationDestinationPrefixes("/pub");
        registry.enableSimpleBroker("/sub");
                /*.setTaskScheduler(new DefaultManagedTaskScheduler())
                .setHeartbeatValue(new long[]{0, 2000});*/
    }

}
