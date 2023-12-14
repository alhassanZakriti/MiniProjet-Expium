package com.example.demo.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.User;
import com.example.demo.service.UserService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/user")
public class UserController {
    /*------------------------------------- Services -------------------------------------*/
    @Autowired
    private UserService userService;

    /*@Autowired
    private ImageService imageService;*/

    /*------------------------------------- Get Methods -------------------------------------*/

    //Get all Users
    @GetMapping("/users")
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userService.findAll().getBody(); 
    
        if (users != null && !users.isEmpty()) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Showing profile picture (just a test)
    @GetMapping("/picture")
    public ResponseEntity<?> findPicture(@RequestParam("username") String username){
        byte[] image = userService.showPicture(username);

        return ResponseEntity.status(HttpStatus.OK)
        .contentType(MediaType.valueOf("image/png"))
        .body(image);
    }

    //Return list of friends for an user
    @GetMapping("/friends")
    public ResponseEntity<List<User>> getFriends(@RequestParam("username") String username) {
        return userService.getAllFriends(username);
    }

    
    /*------------------------------------- Post Methods -------------------------------------*/

    //Sign up
    @PostMapping("/signup")
    public String addUser(
        @RequestParam(value = "username") String username,
        @RequestParam(value = "password") String password,
        @RequestParam(value = "email") String email,
        @RequestParam(value = "name") String name) throws IOException {

        User user = User.builder()
                .username(username)
                .name(name)
                .email(email)
                .password(password)
                .build();

        return userService.addUser(user);
    }

    //Login
    @PostMapping("/login")
    public ResponseEntity<?> login(
        @RequestParam(value = "username") String username,
        @RequestParam(value = "password") String password
    ){
        User user = User.builder()
            .username(username)
            .password(password)
            .build();
        return userService.login(user);
    }

    //upload profile picture for an user
    @PostMapping("/upload-profile-picture")
    public ResponseEntity<?> uploadProfilePicture(
            @RequestParam("username") String username,
            @RequestParam("picture") MultipartFile file) {
        try {
            
            //Image image = imageService.uploadImage(file);
            userService.uploadProfilePicture(username, file);
            
            byte[] image = userService.showPicture(username);
            
            return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image");
        }
    }

    //Adding friends
    @PostMapping("/add-friend")
    public ResponseEntity<String> addFriend(@RequestParam("username1") String username1, @RequestParam("username2") String username2) {
        userService.addFriend(username1, username2);
        return ResponseEntity.ok("Friend added successfully");
    }
    

    //Removing Friend
    @PostMapping("/remove-friend")
    public ResponseEntity<?> removeFriend(@RequestParam("username") String username,@RequestParam("usernameF") String usernameFriend){
        userService.removeFriend(username,usernameFriend);
        return ResponseEntity.ok("Friend removed successfully");
    }
}
