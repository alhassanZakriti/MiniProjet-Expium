package com.example.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Image;

public interface ImageRepo  extends MongoRepository<Image,String>{
    
}
