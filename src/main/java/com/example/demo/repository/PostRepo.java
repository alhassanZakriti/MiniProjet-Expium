package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Post;

public interface PostRepo extends MongoRepository<Post,String>{
    List<Post> findByUserUsername(String username);
    
}
