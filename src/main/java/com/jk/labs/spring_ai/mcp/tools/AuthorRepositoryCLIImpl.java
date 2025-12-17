package com.jk.labs.spring_ai.mcp.tools;

import com.jk.labs.spring_ai.mcp.dto.Author;
import org.springframework.ai.tool.annotation.Tool;

import java.util.List;

public class AuthorRepositoryCLIImpl{

    @Tool(name = "getAuthorByArticleTitleCLI", description = "Get author details using an article title")
    public Author getAuthorByArticleTitle(String articleTitle) {
        return new Author("Kishore Veleti", "Kishore Veleti");
    }

    @Tool(name = "getTopAuthorsCLI", description = "Get highest rated authors")
    public List<Author> getTopAuthors() {
        return List.of(
                new Author("Kishore Veleti", "Kishore Veleti"),
                new Author("Kishore Veleti", "Kishore Veleti")
        );
    }
}
