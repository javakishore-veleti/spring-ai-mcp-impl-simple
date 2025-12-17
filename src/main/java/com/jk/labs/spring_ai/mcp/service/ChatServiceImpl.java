package com.jk.labs.spring_ai.mcp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChatServiceImpl implements ChatService {

    @Override
    public String chat(String question) {
        log.info("Service Received chat request: {}", question);

        String answer = "";

        log.info("Service Answer Generated: {}", answer);
        return answer;
    }
}
