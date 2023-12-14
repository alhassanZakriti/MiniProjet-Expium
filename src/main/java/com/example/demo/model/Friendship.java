package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Friendship {
    @Id
    private String id;

    @DBRef
    private User user1;

    @DBRef
    private User user2;

    public Friendship(User user1, User user2) {
        this.id = generateId(user1.getUsername(), user2.getUsername());
        this.user1 = user1;
        this.user2 = user2;
    }


    


    private String generateId(String username1, String username2) {
        // Concatenate usernames and hash the resulting string
        return Integer.toString(Objects.hash(username1 + username2));
    }

    // Additional methods for checking and getting users

    
    public User getOtherUser(User user) {
        if (user.equals(user1)) {
            return user2;
        } else if (user.equals(user2)) {
            return user1;
        } else {
            throw new IllegalArgumentException("User not part of this friendship");
        }
    }
}

