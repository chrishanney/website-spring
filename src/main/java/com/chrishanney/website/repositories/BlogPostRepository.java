package com.chrishanney.website.repositories;

import com.chrishanney.website.domain.entities.BlogPostEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogPostRepository extends CrudRepository<BlogPostEntity, Long>, PagingAndSortingRepository<BlogPostEntity, Long>
{

}
