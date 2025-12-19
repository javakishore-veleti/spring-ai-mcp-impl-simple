package com.jk.labs.spring_ai.mcp.client.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatClient chatClient;

    @Override
    public String chat(String question) {
        log.info("Service Received chat request: {}", question);

        String answer = chatClient
                .prompt()
                .user(question)
                .call()
                .content();

        log.info("Service Answer Generated: {}", answer);
        return answer;
    }
}
