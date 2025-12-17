package com.jk.labs.spring_ai.mcp.api;

import com.jk.labs.spring_ai.mcp.dto.ChatRequest;
import com.jk.labs.spring_ai.mcp.dto.ChatResponse;
import com.jk.labs.spring_ai.mcp.service.ChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(path = "/api", produces = "application/json", consumes = "application/json")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @PostMapping("/chat")
    ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest chatRequest) {
        log.info("Received chat request: {}", chatRequest);

        String answer = chatService.chat(chatRequest.question());

        log.info("Answer received: {}", answer);
        return ResponseEntity.ok(new ChatResponse(answer));
    }
}
