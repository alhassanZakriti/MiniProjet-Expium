package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Image;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepo;

@Service
public class PostService {
    @Autowired
    private PostRepo repo;

    //Create new Post
    public Post createPost(Post post, User user, MultipartFile file) throws IOException{
        Image image = Image.builder()
        .type(file.getContentType())
        .picture(ImageUtils.compressImage(file.getBytes()))
        .build();

        post.setPostImage(image);
        post.setComments(null);
        post.setUser(user);

        return repo.save(post);
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
