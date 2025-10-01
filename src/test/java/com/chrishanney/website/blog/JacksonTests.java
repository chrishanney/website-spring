package com.chrishanney.website.blog;

import com.chrishanney.website.domain.entities.BlogPostEntity;
import com.chrishanney.website.repositories.TestDataUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.*;
import org.junit.jupiter.api.Test;



import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class JacksonTests {

    @Test
    public void testThatObjectMapperCanCreateJsonFromJavaObject() throws JsonProcessingException {
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        BlogPostEntity blogPostEntity = TestDataUtil.createTestBlogPostA();

        String result = objectMapper.writeValueAsString(blogPostEntity);
        assertThat(result.substring(0, 10)).isEqualTo("{\"id\":1,\"p");
    }

    @Test
    public void testThatObjectMapperCanCreateJavaObjectFromJsonObject() throws JsonProcessingException {
        String json = "{\"id\":1,\"postTitle\":\"Test Title\",\"postContent\":\"This is a test blog post. It has sentences and stuff. It's super great.\",\"date\":1757514131.154904900}";
        ObjectMapper objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        BlogPostEntity result = objectMapper.readValue(json, BlogPostEntity.class);
        assertThat((result.getPostTitle()).equals("Test Title"));
        System.out.println(result.getPostCreatedAt().toString());
//        assertThat(result).isEqualTo(blogPost);
    }
}
