package com.example.demo.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.Role;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    
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

        repo.save(user);
        var jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(AuthenticationResponse.builder()
            .token(jwtToken)
            .build());
    }

public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
    var user = repo.findByUsername(request.getUsername()).orElseThrow();
    
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder()
        .token(jwtToken)
        .build();
}
    
}
