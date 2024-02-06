package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    


    

    @GetMapping("/{username}/posts")
    public List<Map<String, Object>> getUserPosts(@PathVariable String username) {
        return postService.findUserPosts(username);
    }

    @GetMapping("/followingPosts")
    public ResponseEntity<?> findFollowingPosts(@RequestParam("username") String username) {
        List<Map<String, Object>> followingPosts = postService.findFollowingPosts(username);
    
        if (followingPosts.isEmpty()) {
            return ResponseEntity.ok("no post found");
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

    @GetMapping("/{postId}/info")
    public ResponseEntity<?> getPostInfo(@PathVariable("postId") String postId) {
        Map<String, Object> postInfo = postService.getPostInfo(postId);

        if (postInfo.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
        }

        return ResponseEntity.ok(postInfo);
    }
}