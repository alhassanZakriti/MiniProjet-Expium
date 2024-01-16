package com.example.demo.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Image;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.PostRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.tools.ImageUtils;

import org.mindrot.jbcrypt.BCrypt;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    ImageService imgService;

    @Autowired
    PostRepo postRepo;




    /* *********************** Upload Image method start here *********************** */
    public String uploadProfilePicture(String username, MultipartFile file) throws IOException{
        try{
            
            Optional<User> userOptional = userRepo.findByUsername(username);
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Image image = imgService.uploadImage(file);
                // Set the profile picture for the user
                user.setPicture(image);
                // Save the updated user in the database
                userRepo.save(user);
                
                return "Image uploaded";
            } else {
                return "User not found"; // or throw an exception, return an error code, etc.
            }
        }catch (Exception e) {
            // Handle exceptions, log, and potentially rollback the transaction
            throw new RuntimeException("Failed to upload profile picture", e);
        }
        
    }
    /* *********************** Upload Image method Ends here *********************** */


    /*********************************************************************************************************** */




    //To Find All connected Users
    public List<User> findConnectedUsers() {
        return userRepo.findAllByStatus(com.example.demo.service.Status.ONLINE);
    }


    /*********************************************************************************************************** */


    

    /*********************************************************************************************************** */


    /* *********************************** find User method Start *********************************** */
    public Optional<User> findByUsername(String username) {
        // Assume userRepo is your UserRepository or wherever you're retrieving users from
        return userRepo.findByUsername(username);
    }
    /* *********************************** find User method End *********************************** */


    /*********************************************************************************************************** */


    /* *********************************** find all Users method Start *********************************** */
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userRepo.findAll();
        return ResponseEntity.ok(users);
    }
    /* *********************************** find all Users method End *********************************** */


    /*********************************************************************************************************** */


    /* *********************************** Showing Picture method Start *********************************** */
    public byte[] showPicture(String username) {
        Optional<User> userOptional = userRepo.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            byte[] images = ImageUtils.decompressImage(user.getPicture().getPicture());
            return images;
        }

        return new byte[0]; // Handle the case where the user is not found
    }
    /* *********************************** Showing Picture method End *********************************** */


    /* ***----------------------------------------------- Friends Methods -----------------------------------------------*** */


    /* *********************************** Adding Friends method Start *********************************** */
    public void addFriend(String username1, String username2) {
        Optional<User> userOp1 = userRepo.findByUsername(username1);
        Optional<User> userOp2 = userRepo.findByUsername(username2);
    
        if (userOp1.isPresent() && userOp2.isPresent()) {
            User user1 = userOp1.get();
            User user2 = userOp2.get();
    
            // Save the users to ensure they have valid IDs
            userRepo.save(user1);
            userRepo.save(user2);

            // Create a Friendship object and update the users' friendship lists

            user1.getFriendships().add(user2);
            user2.getFriendships().add(user1);

            // Save the updated users
            userRepo.save(user1);
            userRepo.save(user2);
        }
    }
    /* *********************************** Adding Friends method End *********************************** */


    /*********************************************************************************************************** */


    /* *********************************** Removing Friends method Start *********************************** */
    public void removeFriend(String username, String friendUsername) {
        Optional<User> userOp = userRepo.findByUsername(username);
        Optional<User> friendOp = userRepo.findByUsername(friendUsername);

        if (userOp.isPresent() && friendOp.isPresent()) {
            User user = userOp.get();
            User friend = friendOp.get();

            if (user != null && friend != null) {
            user.removeFriend(friend);
            friend.removeFriend(user);

            userRepo.save(user);
            userRepo.save(friend);
            }
        }
        
    }
    /* *********************************** Removing Friends method End *********************************** */


    /*********************************************************************************************************** */


    /* *********************************** Showing All Friends method Start *********************************** */
    public ResponseEntity<List<User>> getAllFriends(String username) {
        Optional<User> userOptional = userRepo.findByUsername(username);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            if (user.getFriendships() != null) {
                List<User> friends = new ArrayList<User>(); 
                
                for (User friend : user.getFriendships()) {
                    // Add friend to the Set only if it's not already present
                    if (friend != null && !friends.contains(friend)) {
                        friends.add(friend);
                    }
                }
                
                return ResponseEntity.ok(new ArrayList<>(friends)); // Convert Set to List before returning
            } else {
                return ResponseEntity.ok(Collections.emptyList());
            }
        } else {
            // Handle case where user is not found
            return ResponseEntity.notFound().build();
        }
    }
    /* *********************************** Showing All Friends method End *********************************** */


    /* ***----------------------------------------------- Friends Methods End -----------------------------------------------*** */

    /* Try */
    public List<Post> findPostsUser(String username){
        User user = userRepo.findByUsername(username).get();
        List<Post> posts = user.getPosts();
        //byte[] images = ImageUtils.decompressImage(posts.get);
        return posts;
    }

    //-----------------------------------------------------------------------

    public ResponseEntity<String> follow(String username1, String username2) {
        User user1 = userRepo.findByUsername(username1).orElse(null);
        User user2 = userRepo.findByUsername(username2).orElse(null);

        if (user1 == null || user2 == null) {
            return ResponseEntity.notFound().build();
        }

        user1.follow(user2);
        userRepo.save(user1);
        userRepo.save(user2);

        return ResponseEntity.ok("User " + username1 + " is now following " + username2);
    }

    public ResponseEntity<String> unfollow(String username1, String username2) {
        User user1 = userRepo.findByUsername(username1).orElse(null);
        User user2 = userRepo.findByUsername(username2).orElse(null);

        if (user1 == null || user2 == null) {
            return ResponseEntity.notFound().build();
        }

        user1.unfollow(user2);
        userRepo.save(user1);
        userRepo.save(user2);

        return ResponseEntity.ok("User " + username1 + " is no longer following " + username2);
    }
}
