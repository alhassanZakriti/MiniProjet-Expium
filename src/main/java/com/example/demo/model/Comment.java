package com.example.demo.model;

import java.time.LocalDateTime;

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
public class Comment {
    @Id
    private String id;

    private String commentContent;

    @JsonIgnore
    @CreatedDate
    private LocalDateTime date;

    @JsonIgnore
    @DBRef
    private User user;

    @JsonIgnore
    @DBRef
    private Post post;
}