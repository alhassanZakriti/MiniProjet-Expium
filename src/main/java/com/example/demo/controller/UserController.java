package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.service.tools.ImageUtils;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/user")
public class UserController {
    /*------------------------------------- Services -------------------------------------*/
    @Autowired
    private UserService userService;

    /*@Autowired
    private ImageService imageService;*/




    /*------------------------------------- Get Methods -------------------------------------*/

    //Get all Users
    @GetMapping("/all-users")
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

    /* Sign-up and login are in Package auth in AuthenticationController */

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

    @GetMapping("/posts")
    public ResponseEntity<List<Map<String, Object>>> findPosts(@RequestParam("username") String username) {
        List<Post> posts = userService.findPostsUser(username);

        List<Map<String, Object>> postDetails = new ArrayList<>();

        for (Post post : posts) {
            Map<String, Object> postInfo = new HashMap<>();
            postInfo.put("content", post.getContent());

            if (post.getPostImage() != null) {
                byte[] uncompressedImage = ImageUtils.decompressImage(post.getPostImage().getPicture());
                String base64Image = Base64.getEncoder().encodeToString(uncompressedImage);
                postInfo.put("postImage", base64Image);
            }

            postDetails.add(postInfo);
        }

        return ResponseEntity.ok(postDetails);
    }

}
