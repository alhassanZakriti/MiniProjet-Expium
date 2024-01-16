package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Token;



public interface TokenRepo extends MongoRepository<Token,String> {
    
} 
