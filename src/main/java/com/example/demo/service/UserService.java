package com.example.demo.service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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



    /*********************************************************************************************************** */



    /*********************************************************************************************************** */


    /* *********************************** Showing All Friends method Start *********************************** */
    public ResponseEntity<List<User>> getAllFriends(String username) {
        Optional<User> userOptional = userRepo.findByUsername(username);
        
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            if (user.getFriends() != null) {
                List<User> friends = new ArrayList<User>(); 
                
                for (User friend : user.getFriends()) {
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
    
        if (user1.getFollowing().contains(user2) && user2.getFollowing().contains(user1)) {
            user1.getFriends().add(user2);
            user2.getFriends().add(user1);
            userRepo.save(user1);
            userRepo.save(user2);
            return ResponseEntity.ok("friends");
        }
    
        return ResponseEntity.ok("following");
    }

    

    /* ***************************** Following posts ***************************** */
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

    /* *********************************** Unfollow method */
    public ResponseEntity<String> unfollow(String username1, String username2) {
        User user1 = userRepo.findByUsername(username1).orElse(null);
        User user2 = userRepo.findByUsername(username2).orElse(null);
    
        if (user1 == null || user2 == null) {
            return ResponseEntity.notFound().build();
        }
    
        user1.unfollow(user2);
        if (user1.getFriends().contains(user2)) {
            user1.getFriends().remove(user2);
            user2.getFriends().remove(user1);
        }
        userRepo.save(user1);
        userRepo.save(user2);
    
        return ResponseEntity.ok("not friends");
    }


    public boolean areFriends(String username1, String username2) {
        User user1 = userRepo.findByUsername(username1).orElse(null);
        User user2 = userRepo.findByUsername(username2).orElse(null);
    
        if (user1 == null || user2 == null) {
            return false;
        }
    
        return user1.getFriends().contains(user2) && user2.getFriends().contains(user1);
    }

    //Filtering posts of following based on date (recent to oldest)
    public List<Map<String, Object>> filterPosts(String username) {
        User currentUser = userRepo.findByUsername(username).orElse(null);
        if (currentUser == null) {
            throw new UsernameNotFoundException("User not found");
        }

        List<User> following = currentUser.getFollowing();
        LocalDateTime currentDateTime = LocalDateTime.now();

        return postRepo.findAll().stream()
            .filter(post -> following.contains(post.getUser()))
            .sorted(Comparator.comparing(Post::getDate).reversed())
            .map(post -> {
                Map<String, Object> postMap = new HashMap<>();
                postMap.put("postId", post.getIdPost());
                postMap.put("content", post.getContent());
                postMap.put("postImage", post.getPostImage());
                postMap.put("likes", post.getLikes());
                postMap.put("timeAgo", calculateTimeAgo(post.getDate(), currentDateTime));
                return postMap;
            })
            .collect(Collectors.toList());
    }

    private String calculateTimeAgo(LocalDateTime postDate, LocalDateTime currentDateTime) {
        Duration duration = Duration.between(postDate, currentDateTime);
        long seconds = duration.getSeconds();

        if (seconds < 60) {
            return seconds + " s";
        } else if (seconds < 3600) {
            return seconds / 60 + " m";
        } else if (seconds < 86400) {
            return seconds / 3600 + " h";
        } else if (seconds < 604800) {
            return seconds / 86400 + " d";
        } else {
            return seconds / 604800 + " w";
        }
    }

    //suggestion friends
    public List<Map<String, Object>> suggestFriends(String username) {
        User currentUser = userRepo.findByUsername(username).orElse(null);
        if (currentUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
    
        List<User> friends = currentUser.getFriends();
        Set<Map<String, Object>> suggestedFriends = new HashSet<>();
    
        if (friends.isEmpty()) {
            // Suggest random users if the current user doesn't have any friends
            List<User> allUsers = userRepo.findAll();
            Collections.shuffle(allUsers);
            for (User user : allUsers.subList(0, Math.min(5, allUsers.size()))) {
                if (!user.equals(currentUser)) {
                    Map<String, Object> suggestedFriend = new HashMap<>();
                    suggestedFriend.put("username", user.getUsername());
                    suggestedFriend.put("name", user.getName());
                    suggestedFriend.put("picture", user.getPicture());
                    suggestedFriend.put("mutualFriends", 0);
                    suggestedFriends.add(suggestedFriend);
                }
            }
        } else {
            for (User friend : friends) {
                List<User> friendsOfFriend = friend.getFriends();
                for (User suggestedFriend : friendsOfFriend) {
                    if (!friends.contains(suggestedFriend) && !suggestedFriend.equals(currentUser)) {
                        Map<String, Object> friendSuggestion = new HashMap<>();
                        friendSuggestion.put("username", suggestedFriend.getUsername());
                        friendSuggestion.put("name", suggestedFriend.getName());
                        friendSuggestion.put("picture", suggestedFriend.getPicture());
                        friendSuggestion.put("mutualFriends", countMutualFriends(friends, friendsOfFriend));
                        suggestedFriends.add(friendSuggestion);
                    }
                }
            }
        }
    
        return new ArrayList<>(suggestedFriends);
    }
    
    private int countMutualFriends(List<User> friends, List<User> friendsOfFriend) {
        Set<User> mutualFriends = new HashSet<>(friends);
        mutualFriends.retainAll(friendsOfFriend);
        return mutualFriends.size();
    }

    public List<User> findConnectedUsers(String username) {
        User user = userRepo.findByUsername(username).orElseThrow();
        List<User> connectedUsers = new ArrayList<>();

        for (User friend : user.getFriends()) {
            if (friend.getStatus() == Status.ONLINE) {
                connectedUsers.add(friend);
            }
        }

        return connectedUsers;
    }
}
