package com.jk.labs.spring_ai.mcp.client.configuration;

import com.jk.labs.spring_ai.mcp.server.tools.AuthorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.mcp.customizer.McpSyncClientCustomizer;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ChatbotConfig {

    @Bean
    ChatClient chatClient(ChatModel chatModel, SyncMcpToolCallbackProvider toolCallbackProvider) {
        return ChatClient
                .builder(chatModel)
                .defaultToolCallbacks(toolCallbackProvider.getToolCallbacks())
                .build();
    }

    @Bean
    ToolCallbackProvider authorTools() {
        return MethodToolCallbackProvider
                .builder()
                .toolObjects(new AuthorRepository())
                .build();
    }

    // Additionally, enabled our MCP server to add or remove tools at runtime dynamically, we can register a listener to detect these tool changes:
    @Bean
    McpSyncClientCustomizer mcpSyncClientCustomizer() {
        return (name, mcpClientSpec) -> {
            mcpClientSpec.toolsChangeConsumer(tools -> {
                log.info("Detected tools changes.");
            });
        };
    }

}
