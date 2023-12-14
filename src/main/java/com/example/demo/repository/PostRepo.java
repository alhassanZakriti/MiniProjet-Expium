package com.example.demo.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Post;

public interface PostRepo extends MongoRepository<Post,ObjectId>{
    List<Post> findByUserUsername(String username);

}
