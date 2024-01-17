package com.example.learnjwt.services;

import com.example.learnjwt.request.LoginRequest;
import com.example.learnjwt.request.RegisterRequest;
import com.example.learnjwt.response.JwtResponse;

public interface IUserService {
    JwtResponse login(LoginRequest loginRequest);
    void register(RegisterRequest registerRequest);

}
