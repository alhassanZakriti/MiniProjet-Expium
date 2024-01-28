package com.example.demo.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Comment;
import com.example.demo.model.Image;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.CommentRepo;
import com.example.demo.repository.PostRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.tools.ImageUtils;

@Service
public class PostService {

    
    @Autowired
    private PostRepo repo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    String postId = UUID.randomUUID().toString();

    //Create new Post
    public Post createPost(Post post, User user, MultipartFile file) throws IOException{
        String postId = UUID.randomUUID().toString();

        if (file != null){
            Image image = Image.builder()
            .type(file.getContentType())
            .picture(ImageUtils.compressImage(file.getBytes()))
            .build();

            post.setIdPost(postId);
            post.setPostImage(image);
            post.setComments(null);
            post.setUser(user);
        } else {
            post.setIdPost(postId);
            post.setComments(null);
            post.setUser(user);
        }

        post.setDate(LocalDateTime.now());



        return repo.save(post);
    }


    //
    public byte[] showPostImage(String postId) {
        Optional<Post> postOptional = repo.findById(postId);
    
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            if (post.getPostImage() != null) {
                return ImageUtils.decompressImage(post.getPostImage().getPicture());
            }
        }
    
        return new byte[0]; // Handle the case where the post is not found or has no image
    }


    //Getting list pf post of an user
    public ResponseEntity<List<Post>> getPosts(User user){

        List<Post> listPost = repo.findByUserUsername(user.getUsername());

        return ResponseEntity.ok(listPost);
    }

    //Get friends post
    public List<Post> getPostsForFriends(List<User> friends) {
        List<Post> friendPosts = new ArrayList<>();

        for (User friend : friends) {
            List<Post> posts = repo.findByUserUsername(friend.getUsername());
            friendPosts.addAll(posts);
        }

        return friendPosts;
    }


    public List<Post> findFollowingPosts(String username) {
        User user = userRepo.findByUsername(username).orElse(null);
    
        if (user == null) {
            return Collections.emptyList();
        }
    
        List<Post> followingPosts = new ArrayList<>();
        for (User followingUser : user.getFollowing()) {
            followingPosts.addAll(followingUser.getPosts());
        }
    
        return followingPosts;
    }

    public List<Map<String, Object>> filterPosts(String username) {
        User currentUser = userRepo.findByUsername(username).orElse(null);
        if (currentUser == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<User> following = currentUser.getFollowing();
        LocalDateTime currentDateTime = LocalDateTime.now();

        return repo.findAll().stream()
            .filter(post -> following.contains(post.getUser()))
            .sorted(Comparator.comparing(Post::getDate).reversed())
            .map(post -> {
                Map<String, Object> postMap = new HashMap<>();
                postMap.put("postId", post.getIdPost());
                postMap.put("content", post.getContent());
                postMap.put("postImage", post.getPostImage());
                postMap.put("likes", post.getLikes());
                postMap.put("timeAgo", userService.calculateTimeAgo(post.getDate(), currentDateTime));
                return postMap;
            })
            .collect(Collectors.toList());
    }

    
    //Delete Post
    public void deletePost(String postId) {
        Post post = repo.findByIdPost(postId).get();
        User user = post.getUser();
        user.removePost(post);
        
        userRepo.save(user);

        repo.delete(post);
    }

    // Edit Post
    public Post editPost(String postId, Post updatedPost) {
        Optional<Post> optionalPost = repo.findById(postId);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setContent(updatedPost.getContent());
            post.setPostImage(updatedPost.getPostImage());
            // Update any other fields as needed
            return repo.save(post);
        } else {
            throw new IllegalArgumentException("Post not found");
        }
    }

    //Comments party I don't wanna create a new service cuz I'm lazy hhhhhh

    @Autowired 
    private CommentRepo commentRepo;

    //String commentId = UUID.randomUUID().toString();

    // Comment on a post
    public Comment createComment(String postId, String commentContent, String username) {
        String commentId = UUID.randomUUID().toString();
        // Find the post
        Post post = repo.findByIdPost(postId)
            .orElseThrow();

        User user = userRepo.findByUsername(username)
            .orElseThrow();
    
        // Create a new comment
        Comment comment = Comment.builder()
            .id(commentId)
            .commentContent(commentContent)
            .user(user)
            .post(post)
            .date(LocalDateTime.now())
            .build();
    
        // Save the comment
        comment = commentRepo.save(comment);
    
        // Add the comment to the post's comments and save the post
        post.addComment(comment);
        repo.save(post);
    
        return comment;
    }

    public List<Map<String, Object>> getAllComments(String idPost) {
        Post post = repo.findByIdPost(idPost).get();
        // Fetch all comments
        List<Comment> comments = post.getComments();
    
        // Sort comments by date in descending order
        comments.sort((comment1, comment2) -> comment2.getDate().compareTo(comment1.getDate()));
    
        LocalDateTime currentDateTime = LocalDateTime.now();
    
        // Convert to Map
        List<Map<String, Object>> commentMaps = comments.stream().map(comment -> {
            User user = comment.getUser();
            Map<String, Object> commentMap = new HashMap<>();
            commentMap.put("username", user.getUsername());
            commentMap.put("commentId", comment.getId());
            commentMap.put("profilePicture", user.getPicture());
            commentMap.put("content", comment.getCommentContent());
            commentMap.put("timeAgo", userService.calculateTimeAgo(comment.getDate(), currentDateTime));
            return commentMap;
        }).collect(Collectors.toList());
    
        return commentMaps;
    }

    public void deleteComment(String commentId) {
    // Find the comment
        Comment comment = commentRepo.findById(commentId)
            .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        // Get the post associated with the comment
        Post post = comment.getPost();

        // Remove the comment from the post's comments
        post.removeComment(comment);

        // Save the post
        repo.save(post);

        // Delete the comment
        commentRepo.delete(comment);
    }
    
}
