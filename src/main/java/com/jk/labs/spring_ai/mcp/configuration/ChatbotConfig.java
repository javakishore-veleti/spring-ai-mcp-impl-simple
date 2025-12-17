package com.jk.labs.spring_ai.mcp.configuration;

import com.jk.labs.spring_ai.mcp.tools.AuthorRepository;
import com.jk.labs.spring_ai.mcp.tools.AuthorRepositoryCLIImpl;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.mcp.customizer.McpSyncClientCustomizer;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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

    @Bean
    CommandLineRunner commandLineRunner(
            McpSyncServer mcpSyncServer,
            @Value("${feature.toggles.author-tools.enabled:false}") boolean authorToolsEnabled
    ) {
        return args -> {
            if (authorToolsEnabled) {
                ToolCallback[] toolCallbacks = ToolCallbacks.from(new AuthorRepositoryCLIImpl());
                List<McpServerFeatures.SyncToolSpecification> tools = McpToolUtils.toSyncToolSpecifications(toolCallbacks);
                tools.forEach(tool -> {
                    mcpSyncServer.addTool(tool);
                    mcpSyncServer.notifyToolsListChanged();
                });
            }
        };
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
