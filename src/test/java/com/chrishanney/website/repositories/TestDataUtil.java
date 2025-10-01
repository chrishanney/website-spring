package com.chrishanney.website.repositories;

import com.chrishanney.website.domain.entities.BlogPostEntity;

import java.time.Instant;

public class TestDataUtil {

    private TestDataUtil() {}
    // probably makes more sense to build a function that takes parameters but I'm being lazy

    public static BlogPostEntity createTestBlogPostA() {

        return BlogPostEntity.builder().
                id(1L)
                .postTitle("Test Title")
                .postContent("This is a test blog post. It has sentences and stuff. It's super great.")
                .postCreatedAt(Instant.now().minusSeconds(86400 * 2))
                .build();
    }

    public static BlogPostEntity createTestBlogPostB() {
        return BlogPostEntity.builder().
                id(2L)
                .postTitle("Test Title Two")
                .postContent("This is the second test blog post. It occurs a day after the first post. It's super great.")
                .postCreatedAt(Instant.now().minusSeconds(86400))
                .build();
    }

    public static BlogPostEntity createTestBlogPostC() {
        return BlogPostEntity.builder().
                id(3L)
                .postTitle("Test Title Three")
                .postContent("This is the third test blog post. It occurs a day after the second post It's super great.")
                .postCreatedAt(Instant.now())
                .build();
    }
}
