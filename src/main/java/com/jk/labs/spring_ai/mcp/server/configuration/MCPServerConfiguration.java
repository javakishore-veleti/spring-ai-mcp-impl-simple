package com.jk.labs.spring_ai.mcp.server.configuration;

import com.jk.labs.spring_ai.mcp.server.tools.AuthorRepositoryCLIImpl;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.server.McpSyncServer;
import org.springframework.ai.mcp.McpToolUtils;
import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class MCPServerConfiguration {

    // --- Runtime conditional tool registration ---
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
}
