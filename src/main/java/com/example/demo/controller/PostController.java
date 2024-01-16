package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.example.demo.repository.PostRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.PostService;
//import com.example.demo.service.UserService;
import com.example.demo.service.tools.ImageUtils;


@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping("/user/post")
public class PostController {

    @Autowired 
    private PostService postService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PostRepo repo;

    /*@Autowired
    private UserService userService;*/
    

    @PostMapping("/create")
    public ResponseEntity<?> createPost(
        @RequestParam("username") String username,
        @RequestParam(value = "picture", required = false) MultipartFile file,
        @RequestParam("content") String content
    ) throws IOException {
        User user = userRepo.findByUsername(username).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        Post post = Post.builder().content(content).build();
        postService.createPost(post, user, file);
        user.addPost(post);
        userRepo.save(user);

        return ResponseEntity.ok("Posted");
    }

    /* *************************************** Showing user posts *************************************** */
    
    
    @GetMapping("/for-friends")
    public List<Post> getPostsForFriends(User currentUser, List<User> friends) {
        List<Post> friendPosts = new ArrayList<>();

        // Include posts of the currently authenticated user
        List<Post> currentUserPosts = repo.findByUserUsername(currentUser.getUsername());
        friendPosts.addAll(currentUserPosts);

        // Include posts of the user's friends
        for (User friend : friends) {
            List<Post> posts = repo.findByUserUsername(friend.getUsername());
            friendPosts.addAll(posts);
        }

        return friendPosts;
    }


    @GetMapping("/image")
    public ResponseEntity<?> showPostImage(@RequestParam("id") String id) {
    try {
        Post post = repo.findById(id).get();

        if (post != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("content", post.getContent());

            if (post.getPostImage() != null) {
                byte[] uncompressedImage = ImageUtils.decompressImage(post.getPostImage().getPicture());
                response.put("postImage", Base64.getEncoder().encodeToString(uncompressedImage));
            }

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving post image");
    }
}
}
