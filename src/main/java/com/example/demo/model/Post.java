package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Post {
    @Id
    private String idPost;
    private String content;
    private Image postImage;
    private Long likes;

    @JsonIgnore
    @CreatedDate
    private LocalDateTime date;

    @JsonIgnore
    @DBRef
    private User user;

    @DBRef
    private List<Comment> comments;
}
