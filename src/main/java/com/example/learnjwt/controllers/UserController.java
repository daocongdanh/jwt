package com.example.learnjwt.controllers;

import com.example.learnjwt.request.LoginRequest;
import com.example.learnjwt.request.RegisterRequest;
import com.example.learnjwt.response.JwtResponse;
import com.example.learnjwt.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest){
        userService.register(registerRequest);

        return ResponseEntity.ok("Register success");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            JwtResponse jwtResponse = userService.login(loginRequest);
            return ResponseEntity.ok(jwtResponse);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
