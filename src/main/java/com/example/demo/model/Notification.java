package com.example.demo.model;

import java.time.LocalDateTime;

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
public class Notification {
    @Id
    private String id;
    private String title;
    private String content;
    private LocalDateTime timestamp;

    @JsonIgnore
    @DBRef
    private User recipient; 
    
    public Notification() {
        this.timestamp = LocalDateTime.now();
    }
}