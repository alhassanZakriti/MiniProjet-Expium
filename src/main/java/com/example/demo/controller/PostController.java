package com.example.demo.controller;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Comment;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.PostService;
import com.example.demo.service.UserService;
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

    @Autowired
    private UserService userService;
    

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
    


    @GetMapping("/post-info")
    public ResponseEntity<?> showPostImage(@RequestParam("id") String id) {
        try {
            Post post = repo.findById(id).get();
    
            if (post != null) {
                Map<String, Object> response = new HashMap<>();
                
                response.put("postId", post.getIdPost());
                response.put("content", post.getContent());
                if (post.getPostImage() != null) {
                    byte[] uncompressedImage = ImageUtils.decompressImage(post.getPostImage().getPicture());
                    response.put("postImage", Base64.getEncoder().encodeToString(uncompressedImage));
                }
    
                // Calculate the difference in minutes, hours, days, and weeks
                long minutes = Duration.between(post.getDate(), LocalDateTime.now()).toMinutes();
                long hours = minutes / 60;
                long days = hours / 24;
                long weeks = days / 7;
    
                String timeAgo;
                if (weeks > 0) {
                    timeAgo = weeks +"w";
                } else if (days > 0) {
                    timeAgo = days + "d";
                } else if (hours > 0) {
                    timeAgo = hours + "h";
                } else {
                    timeAgo = minutes + "m";
                }
    
                response.put("Date", timeAgo);
    
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving post image");
        }
    }

    @GetMapping("/user-posts")
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

    @GetMapping("/followingPosts")
    public ResponseEntity<List<Post>> findFollowingPosts(@RequestParam("username") String username) {
        List<Post> followingPosts = postService.findFollowingPosts(username);

        if (followingPosts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(followingPosts);
    }

    //for multi posts
    @GetMapping("/filter")
    public ResponseEntity<?> filterPosts(@RequestParam("username") String username) {
        try {
            List<Map<String, Object>> posts = postService.filterPosts(username);
    
            if (posts.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No posts found");
            }
    
            return ResponseEntity.ok(posts);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("/like")
    public ResponseEntity<?> likePost(@RequestParam("postId") String postId, @RequestParam("username") String username) {
        try {
            User currentUser = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            Post post = repo.findByIdPost(postId).orElseThrow();
            String postUser = post.getUser().getUsername();
            User recipient = userRepo.findByUsername(postUser).get();

            if (post.getLikes().contains(currentUser)) {
                post.getLikes().remove(currentUser);
            } else {
                post.getLikes().add(currentUser);
                userService.createNotification(currentUser.getUsername(), recipient.getUsername(), "liked your post");
            }

            repo.save(post);

            return ResponseEntity.ok().body("Post liked successfully");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/likes-number")
    public ResponseEntity<?> getLikesCount(@RequestParam("postId")String postId) {

    Post post = repo.findByIdPost(postId).get();
    int likesCount = post.getLikes().size();
    return ResponseEntity.ok().body(likesCount);

    }

    @GetMapping("/likes/users")
    public ResponseEntity<?> getUsersWhoLikedPost(@RequestParam("postId") String postId) {
        
        Post post = repo.findByIdPost(postId).get();
        List<User> usersWhoLiked = post.getLikes();
        return ResponseEntity.ok().body(usersWhoLiked);
        
    }

    @PutMapping("/edit/{postId}")
    public ResponseEntity<Post> editPost(@PathVariable String postId, @RequestBody Post updatedPost) {
        try {
            Post post = postService.editPost(postId, updatedPost);
            return ResponseEntity.ok(post);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deletePost(@RequestParam("postId") String postId) {

        postService.deletePost(postId);
        return ResponseEntity.ok("Deleted");

    }

    @PostMapping("/comment")
    public ResponseEntity<Comment> createComment(@RequestParam("postId") String postId, @RequestParam("content") String commentContent, @RequestParam("username") String username) {
        
        Comment comment = postService.createComment(postId, commentContent, username);
        return ResponseEntity.ok(comment);
        
    }

    @GetMapping("/comments/{postId}")
    public ResponseEntity<List<Map<String, Object>>> getAllComments(@PathVariable String postId) {
        try {
            List<Map<String, Object>> comments = postService.getAllComments(postId);
            return ResponseEntity.ok(comments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/comment/delete")
    public ResponseEntity<?> deleteComment(@RequestParam("commantId")  String commentId) {
        
        postService.deleteComment(commentId);
        return ResponseEntity.ok("Deleted");
        
    }
}