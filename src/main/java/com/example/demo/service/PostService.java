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

import com.example.demo.model.Image;
import com.example.demo.model.Post;
import com.example.demo.model.User;
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
    public void deletePost(){
        
    }

}
