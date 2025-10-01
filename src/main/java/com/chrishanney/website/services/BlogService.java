package com.chrishanney.website.services;

import com.chrishanney.website.domain.DTO.BlogPostDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BlogService {
    BlogPostDTO saveBlogPost(BlogPostDTO post);

    List<BlogPostDTO> findAll();

    Page<BlogPostDTO> findAll(Pageable pageable);

    BlogPostDTO findOne(Long id);

    BlogPostDTO partialUpdate(Long id, BlogPostDTO blogPost);

    boolean isExists(Long id);

    void delete(Long id);
}
