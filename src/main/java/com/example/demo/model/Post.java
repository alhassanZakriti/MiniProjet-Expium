package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Document
public class Post {
    @Id
    private String idPost;
    private String content;
    private Image postImage;

    @JsonIgnore
    @DBRef
    private List<User> likes;

    @JsonIgnore
    @CreatedDate
    private LocalDateTime date;

    @JsonIgnore
    @DBRef
    private User user;

    @DBRef
    private List<Comment> comments;

    public Post(){
        this.comments = new ArrayList<>();
        this.likes = new ArrayList<>();
    }

    public void addLike(User user) {
        if (!likes.contains(user)) {
            likes.add(user);
        }
    }

    public void removeLike(User user) {
        likes.remove(user);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
    }
}