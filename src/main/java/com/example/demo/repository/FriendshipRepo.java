package com.example.demo.repository;

import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.demo.model.Friendship;
import com.example.demo.model.User;

public interface FriendshipRepo extends MongoRepository<Friendship,String>{
    List<Friendship> findByUser1OrUser2(User user1, User user2);
    List<Friendship> findByUser1OrUser2In(User user, List<User> users);
}
