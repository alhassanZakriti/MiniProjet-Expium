package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.TokenRepo;

@Service
public class TokenService {
    @Autowired
    private TokenRepo repo;

    public void revokeToken(String token) {
        var tokenObj = repo.findById(token).orElseThrow();
        tokenObj.setRevoked(true);
        repo.save(tokenObj);
    }

    public boolean isRevoked(String token) {
        var tokenObj = repo.findById(token).orElseThrow();
        return tokenObj.isRevoked();
    }

    public void expireToken(String token) {
        var tokenObj = repo.findById(token).orElseThrow();
        tokenObj.setExpired(true);
        repo.save(tokenObj);
    }

    public boolean isExpired(String token) {
        var tokenObj = repo.findById(token).orElseThrow();
        return tokenObj.isExpired();
    }
}
