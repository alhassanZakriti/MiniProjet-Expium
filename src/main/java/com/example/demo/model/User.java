package com.example.demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
@Document
public class User implements UserDetails{
    @Id
    private String username;
    private String name;
    private String email;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private Image picture; 

    private com.example.demo.service.Status status;

    private Role role;



    @DBRef
    private Token token;

    @JsonIgnore
    @DBRef
    private List<User> friendships;

    @JsonIgnore
    @DBRef
    private List<Post> posts;

    /* test following & followers */
    @JsonIgnore
    @DBRef
    private List<User> followers;

    @JsonIgnore
    @DBRef
    private List<User> following;
    //---------------------------------------

    public User() {
        this.friendships = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    //Follower methods
    public void follow(User userToFollow) {
        if (!following.contains(userToFollow)) {
            following.add(userToFollow);
            userToFollow.getFollowers().add(this);
        }
    }

    public void unfollow(User userToUnfollow) {
        if (following.contains(userToUnfollow)) {
            following.remove(userToUnfollow);
            userToUnfollow.getFollowers().remove(this);
        }
    }
    
}
