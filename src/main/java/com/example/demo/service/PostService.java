package com.example.demo.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Image;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepo;
import com.example.demo.service.tools.ImageUtils;

@Service
public class PostService {
    @Autowired
    private PostRepo repo;


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


    //Tryyyyyyyyyyyyyyyyyyyyyyyyyyyy
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

    //Delete Post
    public void deletePost(){
        
    }

}
