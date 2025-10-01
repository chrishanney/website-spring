package com.chrishanney.website.controllers;

import com.chrishanney.website.domain.entities.BlogPostEntity;
import com.chrishanney.website.repositories.BlogPostRepository;
import com.chrishanney.website.repositories.TestDataUtil;
import com.chrishanney.website.services.BlogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BlogControllerIntegrationTest {

    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    private BlogPostRepository blogPostRepository;

    @Autowired
    private BlogControllerIntegrationTest(MockMvc mockMvc, ObjectMapper objectMapper, BlogPostRepository blogPostRepository) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.blogPostRepository= blogPostRepository;
    }

    @Test
    public void testThatBlogPostSuccessfullyReturnsHttp201Created() throws Exception {
        BlogPostEntity testBlogPostA = TestDataUtil.createTestBlogPostA();
        testBlogPostA.setId(null);
        String blogPostJson = objectMapper.writeValueAsString(testBlogPostA);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/blog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(blogPostJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatBlogPostSuccessfullyReturnsSavedBlogPost() throws Exception {
        BlogPostEntity testBlogPostA = TestDataUtil.createTestBlogPostA();
        testBlogPostA.setId(null);
        String blogPostJson = objectMapper.writeValueAsString(testBlogPostA);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/blog")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(blogPostJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath(".postTitle").value("Test Title")
        ).andExpect(
                MockMvcResultMatchers.jsonPath(".postContent").value("This is a test blog post. It has sentences and stuff. It's super great.")
        );
    }

    @Test
    public void testThatListBlogPostsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/blog")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }
/*
    @Test
    public void testThatListBlogPostsReturnsListOfBlogPosts() throws Exception {
        BlogPostEntity testBlogPostEntityA = TestDataUtil.createTestBlogPostA();
        testBlogPostEntityA.setId(null);
        blogPostRepository.save(testBlogPostEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/blog")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("[0].postTitle").value("Test Title")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("[0].postContent").value("This is a test blog post. It has sentences and stuff. It's super great.")
        );
    }
*/
    @Test
    public void testThatGetBlogPostReturnsHttpStatus200WhenBlogPostExists() throws Exception {
        BlogPostEntity testBlogPostEntityA = TestDataUtil.createTestBlogPostA();
        testBlogPostEntityA.setId(null);
        blogPostRepository.save(testBlogPostEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/blog/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void testThatGetBlogPostReturnsHttpStatus404WhenNoBlogPostExists() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/blog/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());

    }

    @Test
    public void testThatGetBlogPostReturnsBlogPostWhenBlogPostExists() throws Exception {
        BlogPostEntity testBlogPostEntityA = TestDataUtil.createTestBlogPostA();
        testBlogPostEntityA.setId(null);
        blogPostRepository.save(testBlogPostEntityA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/blog/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath(".postTitle").value("Test Title")
        ).andExpect(
                MockMvcResultMatchers.jsonPath(".postContent").value("This is a test blog post. It has sentences and stuff. It's super great.")
        );
    }


    @Test
    public void testThatFullUpdateBlogPostSuccessfullyReturnsHttp200OK() throws Exception {
        BlogPostEntity testBlogPostA = TestDataUtil.createTestBlogPostA();
        testBlogPostA.setId(null);
        blogPostRepository.save(testBlogPostA);
        testBlogPostA.setPostTitle("New Test Title");
        String blogPostJson = objectMapper.writeValueAsString(testBlogPostA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/blog/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(blogPostJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
    @Test
    public void testThatFullUpdateBlogPostFailureReturnsNotFound404WhenNoBlogPostExists() throws Exception {
        BlogPostEntity testBlogPostA = TestDataUtil.createTestBlogPostA();
        testBlogPostA.setId(null);
        blogPostRepository.save(testBlogPostA);
        testBlogPostA.setPostTitle("New Test Title");
        String blogPostJson = objectMapper.writeValueAsString(testBlogPostA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/blog/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(blogPostJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateBlogPostSuccessfullyUpdatesBlogPost() throws Exception {
        BlogPostEntity testBlogPostA = TestDataUtil.createTestBlogPostA();
        testBlogPostA.setId(null);
        blogPostRepository.save(testBlogPostA);
        testBlogPostA.setPostTitle("New Test Title");
        String blogPostJson = objectMapper.writeValueAsString(testBlogPostA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/blog/1") // could do blog + testBlogPostA.getId
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(blogPostJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath(".postTitle").value("New Test Title")
        ).andExpect(
                MockMvcResultMatchers.jsonPath(".postContent").value("This is a test blog post. It has sentences and stuff. It's super great.")
        );
    }


    @Test
    public void testThatPartialUpdateBlogPostSuccessfullyReturnsHttp200OK() throws Exception {
        BlogPostEntity testBlogPostA = TestDataUtil.createTestBlogPostA();
        testBlogPostA.setId(null);
        BlogPostEntity result = blogPostRepository.save(testBlogPostA);
        testBlogPostA.setPostTitle("New Test Title");
        String blogPostJson = objectMapper.writeValueAsString(testBlogPostA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/blog/" + result.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(blogPostJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }


    @Test
    public void testThatPartialUpdateBlogPostFailureReturnsNotFound404WhenNoBlogPostExists() throws Exception {
        BlogPostEntity testBlogPostA = TestDataUtil.createTestBlogPostA();
        testBlogPostA.setId(null);
        blogPostRepository.save(testBlogPostA);
        testBlogPostA.setPostTitle("New Test Title");
        String blogPostJson = objectMapper.writeValueAsString(testBlogPostA);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/blog/99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(blogPostJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdateBlogPostSuccessfullyUpdatesBlogPost() throws Exception {
        BlogPostEntity testBlogPostA = TestDataUtil.createTestBlogPostA();
        testBlogPostA.setId(null);
        blogPostRepository.save(testBlogPostA);
        testBlogPostA.setPostTitle("New Test Title");
        testBlogPostA.setPostContent(null);
        String blogPostJson = objectMapper.writeValueAsString(testBlogPostA);
        mockMvc.perform(
                MockMvcRequestBuilders.patch("/blog/1") // could do blog + testBlogPostA.getId
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(blogPostJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath(".postTitle").value("New Test Title")
        ).andExpect(
                MockMvcResultMatchers.jsonPath(".postContent").value("This is a test blog post. It has sentences and stuff. It's super great.")
        );
    }


    @Test
    public void testThatDeleteReturnsHttpStatus204ForNonExistingBlogPost() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/blog/999") // could do blog + testBlogPostA.getId
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());

    }
    @Test
    public void testThatDeleteReturnsHttpStatus204ForExistingBlogPost() throws Exception {
        BlogPostEntity testBlogPostA = TestDataUtil.createTestBlogPostA();
        testBlogPostA.setId(null);
        blogPostRepository.save(testBlogPostA);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/blog/1") // could do blog + testBlogPostA.getId
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());

    }

}
