package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@Controller
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/user/chat")
public class UserWebsocketController {

    @Autowired
    private UserService userService;

    @MessageMapping("/user.addUser")
    @SendTo("/public")
    public User addUser(@Payload User user) {
        Optional<User> userOptional = userService.findByUsername(user.getUsername());

        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            // Handle the case where the user is not found
            // You might want to create a new user, log an error, or return an error response
            return null; // or throw an exception, depending on your requirements
        }
    }


    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUsers() {
        return ResponseEntity.ok(userService.findConnectedUsers());
    }
}
