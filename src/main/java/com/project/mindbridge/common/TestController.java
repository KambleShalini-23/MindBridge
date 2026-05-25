package com.project.mindbridge.common;

import com.project.mindbridge.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

    private final JwtService jwtService;

    @GetMapping("/test")
    public String test() {
        return "MindBridge test endpoint is working";
    }

    @GetMapping("/health")
    public String health() {
        return "MindBridge backend is running";
    }

    @GetMapping("/protected")
    public String protectedEndpoint() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailId = auth.getName();
//    public String protectedEndpoint(@RequestHeader("Authorization") String authHeader) {
//        System.out.println("Auth header: " + authHeader);
//        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
//            throw new RuntimeException("Missing or invalid Authorization header");
//        }
//
//        String token = authHeader.substring(7);
//        String emailId = jwtService.extractEmail(token);
//
//        if(emailId == null) {
//            throw new RuntimeException("Invalid token");
//        }
//
//        if(!jwtService.isTokenValid(token,emailId)) {
//            throw new RuntimeException("Token validation failed");
//        }
        return "This is a protected endpoint" + " Your email: " + emailId;
    }

    @GetMapping("/admin")
    public String adminEndpoint() {
        return "Admin access";
    }

    @GetMapping("/patient")
    public String patientEndpoint() {
        return "Patient access";
    }
}

