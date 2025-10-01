package com.chrishanney.website.controllers;

import com.chrishanney.website.domain.DTO.BlogPostDTO;
import com.chrishanney.website.services.BlogService;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log
public class BlogController {

    private BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }
    @PostMapping(path="/blog")
    public ResponseEntity<BlogPostDTO> createBlogPost(@RequestBody BlogPostDTO post) {
        return new ResponseEntity<>(blogService.saveBlogPost(post), HttpStatus.CREATED);
    }
    @GetMapping(path="/blog")
    public Page<BlogPostDTO> listPosts(Pageable pageable) {
        return(blogService.findAll(pageable));
    }

    @GetMapping(path="/blog/{id}")
    public ResponseEntity<BlogPostDTO> getBlogPost(@PathVariable("id") Long id) {
        BlogPostDTO foundBlogPost = blogService.findOne(id);
        if(foundBlogPost == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(foundBlogPost, HttpStatus.OK);
    }

    @PutMapping(path="/blog/{id}")
    public ResponseEntity<BlogPostDTO> fullUpdateBlogPost(@PathVariable("id") Long id,
                                                          @RequestBody BlogPostDTO blogPostDTO) {
        if(!blogService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        blogPostDTO.setId(id);
        return new ResponseEntity<>(blogService.saveBlogPost(blogPostDTO), HttpStatus.OK);
    }

    @PatchMapping(path="/blog/{id}")
    public ResponseEntity<BlogPostDTO> partialUpdateBlogPost(@PathVariable("id") Long id,
                                                          @RequestBody BlogPostDTO blogPostDTO) {
        if(!blogService.isExists(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        blogPostDTO.setId(id);
        return new ResponseEntity<>(blogService.partialUpdate(id, blogPostDTO), HttpStatus.OK);
    }

    @DeleteMapping(path="blog/{id}")
    public ResponseEntity deleteBlogPost(@PathVariable("id") Long id) {
        blogService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
