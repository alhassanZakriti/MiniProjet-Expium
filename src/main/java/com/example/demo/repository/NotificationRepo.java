package com.example.demo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Notification;
import com.example.demo.model.User;


public interface NotificationRepo extends MongoRepository<Notification,String>{
    List<Notification> findByRecipient(User recipient);
}
