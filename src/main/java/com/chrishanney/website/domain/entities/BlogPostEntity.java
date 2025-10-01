package com.chrishanney.website.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "blogposts")
// setting this annotation means the ObjectMapper will just ignore fields it doesn't recognize
@JsonIgnoreProperties(ignoreUnknown = true)
public class BlogPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blogpost_id_seq")
    private Long id;

    @Column(name = "postTitle")
    private String postTitle;

    @Column(name = "postContent")
    private String postContent;

    @Column(name = "postCreatedAt")
    @JsonProperty("date")
    private Instant postCreatedAt;

    public String toString() {
        return ("Title: " + this.getPostTitle() + "\nDate: " + this.getPostCreatedAt().toString() + "\nContent: " + this.getPostContent());
    }
}

/* Claude suggestion:

@Entity
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "blog_post_tags",
        joinColumns = @JoinColumn(name = "blog_post_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private Set<Tag> tags = new HashSet<>();
}

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    // constructors, getters, setters
}
 */
