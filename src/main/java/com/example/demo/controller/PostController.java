package com.example.demo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


@RestController
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
        @RequestParam("picture") MultipartFile file,
        @RequestParam("content") String content
    ) throws IOException {
        User user = userRepo.findByUsername(username).get();
        Post post = Post.builder().content(content).build();
        postService.createPost(post, user, file);
        return ResponseEntity.ok("Posted");
    }

    @GetMapping("/list")
    public ResponseEntity<List<Post>> listPosts() {
        // Implementation
        return null;
    }

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


}
