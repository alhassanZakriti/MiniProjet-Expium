package com.example.demo.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.example.demo.repository.TokenRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutService  implements LogoutHandler {
    private final TokenRepo tokenRepo;

    @Override
    public void logout(
        HttpServletRequest request,
        HttpServletResponse response,
        Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
        return;
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepo.findById(jwt)
            .orElse(null);
        if (storedToken != null) {
        storedToken.setExpired(true);
        storedToken.setRevoked(true);
        tokenRepo.save(storedToken);
        SecurityContextHolder.clearContext();
        }
    }
    
}
