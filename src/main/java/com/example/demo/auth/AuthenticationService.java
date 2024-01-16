package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Role;
import com.example.demo.model.Token;
import com.example.demo.model.User;
import com.example.demo.repository.TokenRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
    @Autowired
    private TokenRepo tokenRepo;

    @Autowired
    private UserRepo repo;

    @Autowired 
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;


    public ResponseEntity<?> register(RegisterRequest request) {

        if (repo.existsByUsername(request.getUsername())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Username already used");
        }
    
        // Check if email is already taken
        if (repo.existsByEmail(request.getEmail())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Email already used");
        }

        
        var user = User.builder()
            .name(request.getName())
            .username(request.getUsername())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.USER)
            .build();
        
        var jwtToken = jwtService.generateToken(user);

        Token tk = Token.builder()
            .token(jwtToken)
            .expired(false)
            .build();

        user.setToken(tk);

        repo.save(user);
        tokenRepo.save(tk);

        return ResponseEntity.ok(AuthenticationResponse.builder()
            .token(jwtToken)
            .build());
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        var user = repo.findByUsername(request.getUsername()).orElseThrow();
        
        var jwtToken = jwtService.generateToken(user);
        Token tk = Token.builder()
                .token(jwtToken)
                .expired(false)
                .build();

        user.setToken(tk);
        
        repo.save(user);
        tokenRepo.save(tk);

        return AuthenticationResponse.builder()
            .token(jwtToken)
            .build();
    }

    public ResponseEntity<?> logout(String username) {
        var user = repo.findByUsername(username).orElseThrow();
    
        // Remove the token from the repository
        Token token = user.getToken();
        if (token != null) {
            tokenRepo.delete(token);
        }
    
        // Set user's token to null
        user.setToken(null);
    
        // Save changes to the user
        repo.save(user);
    
        return ResponseEntity.ok("Logged out successfully");
    }
    
}

