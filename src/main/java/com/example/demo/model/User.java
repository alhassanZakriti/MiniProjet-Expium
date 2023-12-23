package com.example.demo.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
public class User {
    @Id
    private String username;
    private String name;
    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private Image picture; 

    private com.example.demo.service.Status status;



    @JsonIgnore
    @DBRef
    private List<User> friendships;

    @JsonIgnore
    @DBRef
    private List<Post> posts;

    public User() {
        this.friendships = new ArrayList<>();
        this.posts = new ArrayList<>();
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public void removeFriend(User friend) {
        friendships.remove(friend);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }
    
}
