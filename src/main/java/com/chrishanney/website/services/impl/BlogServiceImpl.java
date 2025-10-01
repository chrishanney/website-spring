package com.chrishanney.website.services.impl;

import com.chrishanney.website.domain.DTO.BlogPostDTO;
import com.chrishanney.website.domain.entities.BlogPostEntity;
import com.chrishanney.website.mappers.Mapper;
import com.chrishanney.website.repositories.BlogPostRepository;
import com.chrishanney.website.services.BlogService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class BlogServiceImpl implements BlogService {

    private Mapper<BlogPostEntity, BlogPostDTO> blogPostMapper;
    private BlogPostRepository blogPostRepository;

    BlogServiceImpl(Mapper<BlogPostEntity, BlogPostDTO> blogPostMapper, BlogPostRepository blogPostRepository) {
        this.blogPostMapper = blogPostMapper;
        this.blogPostRepository = blogPostRepository;
    }

    public BlogPostDTO saveBlogPost(BlogPostDTO blogPost) {
        BlogPostEntity blogPostEntity = blogPostMapper.mapFrom(blogPost);
        blogPostEntity.setPostCreatedAt(Instant.now());
        BlogPostEntity result = blogPostRepository.save(blogPostEntity);
        return blogPostMapper.mapTo(result);
    }

    public List<BlogPostDTO> findAll() {
        List<BlogPostEntity> blogPosts = StreamSupport.stream(blogPostRepository.findAll().spliterator(), false)
                .toList();

        return blogPosts.stream()
                .map(blogPostMapper::mapTo)
                .collect(Collectors.toList());
    }

    public Page<BlogPostDTO> findAll(Pageable pageable) {
        Page<BlogPostEntity> blogPosts = blogPostRepository.findAll(pageable);
        return blogPosts.map(blogPostMapper::mapTo);
    }
    public BlogPostDTO findOne(Long id) {
        Optional<BlogPostEntity> blogPostFound = blogPostRepository.findById(id);
        return blogPostFound.map(postEntity -> blogPostMapper.mapTo(postEntity)).orElse(null);

    }

    public BlogPostDTO partialUpdate(Long id, BlogPostDTO blogPost) {
        BlogPostEntity blogPostEntity = blogPostMapper.mapFrom(blogPost);
        blogPostEntity.setId(id);

        BlogPostEntity result = blogPostRepository.findById(id).map(existingBlogPost -> {
            Optional.ofNullable(blogPostEntity.getPostTitle()).ifPresent(existingBlogPost::setPostTitle);
            Optional.ofNullable(blogPostEntity.getPostContent()).ifPresent(existingBlogPost::setPostContent);
            return blogPostRepository.save(existingBlogPost);
        }).orElseThrow(() -> new RuntimeException("BlogPost does not exist"));
        return blogPostMapper.mapTo(result);
    }

    public boolean isExists(Long id) {
        return blogPostRepository.existsById(id);
    }

    public void delete(Long id) {
        blogPostRepository.deleteById(id);
    }
}
