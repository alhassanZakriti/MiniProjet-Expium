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
import org.springframework.web.bind.annotation.PathVariable;
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

    @MessageMapping("/user.loginUser")
    @SendTo("/user/public")
    public User loginUser(
            @Payload User user
    ) {

        Optional<User> userOp = userService.findByUsername(user.getUsername());
        if(userOp.isPresent()){        
            return user;
        }

        else{return new User();}
        
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<List<User>> findConnectedUsers(@PathVariable String username) {
        return ResponseEntity.ok(userService.findConnectedUsers(username));
    }

    
}
