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
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Image;
import com.example.demo.model.Notification;
import com.example.demo.model.Post;
import com.example.demo.model.User;
import com.example.demo.repository.NotificationRepo;
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

    @Autowired
    NotificationRepo notificationRepo;



    /* *********************** Upload Image method *********************** */
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
    

    /* *********************************** find User method *********************************** */
    public Optional<User> findByUsername(String username) {
        // Assume userRepo is your UserRepository or wherever you're retrieving users from
        return userRepo.findByUsername(username);
    }
    
    
    /* *********************************** find all Users method *********************************** */
    public ResponseEntity<List<User>> findAll() {
        List<User> users = userRepo.findAll();
        return ResponseEntity.ok(users);
    }


    /* *********************************** Showing Picture method *********************************** */
    public byte[] showPicture(String username) {
        Optional<User> userOptional = userRepo.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            byte[] images = ImageUtils.decompressImage(user.getPicture().getPicture());
            return images;
        }

        return new byte[0]; // Handle the case where the user is not found
    }
    

    /* *********************************** Showing All Friends *********************************** */
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
    


    //-----------------------------------------------------------------------

    public ResponseEntity<String> follow(String username1, String username2) {
        User user1 = userRepo.findByUsername(username1).orElse(null);
        User user2 = userRepo.findByUsername(username2).orElse(null);

        String status ;
    
        if (user1 == null || user2 == null) {
            return ResponseEntity.notFound().build();
        }
    
        if(user1.getFollowing().contains(user2)){
            user1.unfollow(user2);
            if (user1.getFriends().contains(user2)) {
                user1.getFriends().remove(user2);
                user2.getFriends().remove(user1);
            }
            status = "not friends";
        }else{
            user1.follow(user2);
            createNotification(username1,username2, "started following you");
            status = "started following you";
        }
        
        userRepo.save(user1);
        userRepo.save(user2);
        

        
    
        if (user1.getFollowing().contains(user2) && user2.getFollowing().contains(user1)) {
            user1.getFriends().add(user2);
            user2.getFriends().add(user1);
            userRepo.save(user1);
            userRepo.save(user2);

            createNotification(username1, username2, "and you become friends");
            
            return ResponseEntity.ok("friends");
        }
    
        return ResponseEntity.ok(status);
    }


    


    public boolean areFriends(String username1, String username2) {
        User user1 = userRepo.findByUsername(username1).orElse(null);
        User user2 = userRepo.findByUsername(username2).orElse(null);
    
        if (user1 == null || user2 == null) {
            return false;
        }
    
        return user1.getFriends().contains(user2) && user2.getFriends().contains(user1);
    }


    public String calculateTimeAgo(LocalDateTime postDate, LocalDateTime currentDateTime) {
        Duration duration = Duration.between(postDate, currentDateTime);
        long seconds = duration.getSeconds();

        if (seconds < 60) {
            return seconds + "s";
        } else if (seconds < 3600) {
            return seconds / 60 + "m";
        } else if (seconds < 86400) {
            return seconds / 3600 + "h";
        } else if (seconds < 604800) {
            return seconds / 86400 + "d";
        } else {
            return seconds / 604800 + "w";
        }
    }

    //suggestion friends
    public List<Map<String, Object>> suggestFriends(String username) {
        User currentUser = userRepo.findByUsername(username).get();
        if (currentUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
    
        List<User> friends = currentUser.getFriends();
        List<User> following = currentUser.getFollowing(); // Get the list of users the current user is following
        Set<Map<String, Object>> suggestedFriends = new HashSet<>();
    
        List<User> allUsers = userRepo.findAll();
        Collections.shuffle(allUsers);
    
        if (friends.isEmpty() || friends.stream().allMatch(friend -> friend.getFriends().size() <= 3)) {
            for (User user : allUsers.subList(0, Math.min(5, allUsers.size()))) {
                if (!user.equals(currentUser) && !following.contains(user)) { // Check if the current user is following the user
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
                    if (!friends.contains(suggestedFriend) && !suggestedFriend.equals(currentUser) && !following.contains(suggestedFriend)) { // Check if the current user is following the suggested friend
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

    //------------------------- Notifications
    String notificationId = UUID.randomUUID().toString();

    public void createNotification(String senderUsername, String recipientUsername, String content) {
        User sender = userRepo.findByUsername(senderUsername).orElseThrow();
        User recipient = userRepo.findByUsername(recipientUsername).get();
        LocalDateTime timestamp = LocalDateTime.now();
    
        Notification notification = new Notification();
        notification.setId(notificationId);
        notification.setContent(content);
        notification.setTitle(sender.getName());
        notification.setTimestamp(timestamp);
        notification.setRecipient(recipient);
    
        notificationRepo.save(notification); 
    }

    //show all notifications (need to make it from newest to oldest)
    public List<Map<String, Object>> getNotificationsForUser(String username) {
        User currentUser = userRepo.findByUsername(username).orElse(null);
        if (currentUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
    
        LocalDateTime currentDateTime = LocalDateTime.now();
    
        return notificationRepo.findByRecipient(currentUser).stream()
            .sorted(Comparator.comparing(Notification::getTimestamp).reversed())
            .map(notification -> {
                Map<String, Object> notificationMap = new HashMap<>();
                notificationMap.put("title", notification.getTitle());
                notificationMap.put("content", notification.getContent());
                notificationMap.put("timeAgo", calculateTimeAgo(notification.getTimestamp(), currentDateTime));
                return notificationMap;
            })
            .collect(Collectors.toList());
    }
}
