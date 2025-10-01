package com.chrishanney.website.domain.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlogPostDTO {

    private Long id;

    private String postTitle;

    private String postContent;

//    private Instant postCreatedAt;
}
