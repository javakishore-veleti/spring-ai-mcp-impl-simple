package com.jk.labs.spring_ai.mcp.server.tools;

import com.jk.labs.spring_ai.mcp.common.dto.Author;
import org.springframework.ai.tool.annotation.Tool;

import java.util.List;

public class AuthorRepository {

    @Tool(description = "Get author details using an article title")
    public Author getAuthorByArticleTitle(String articleTitle) {
        return new Author("John Doe", "john.doe@baeldung.com");
    }

    @Tool(description = "Get highest rated authors")
    public List<Author> getTopAuthors() {
        return List.of(
                new Author("John Doe", "john.doe@baeldung.com"),
                new Author("Jane Doe", "jane.doe@baeldung.com")
        );
    }
}