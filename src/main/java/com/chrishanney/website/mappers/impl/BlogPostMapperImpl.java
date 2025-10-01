package com.chrishanney.website.mappers.impl;

import com.chrishanney.website.domain.DTO.BlogPostDTO;
import com.chrishanney.website.domain.entities.BlogPostEntity;
import com.chrishanney.website.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BlogPostMapperImpl implements Mapper<BlogPostEntity, BlogPostDTO> {

    private ModelMapper modelMapper;

    public BlogPostMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Override
    public BlogPostDTO mapTo(BlogPostEntity blogPostEntity) {
        return modelMapper.map(blogPostEntity, BlogPostDTO.class);
    }

    @Override
    public BlogPostEntity mapFrom(BlogPostDTO blogPostDTO) {
        return modelMapper.map(blogPostDTO, BlogPostEntity.class);
    }

}
