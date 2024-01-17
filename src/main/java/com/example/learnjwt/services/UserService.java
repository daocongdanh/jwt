package com.example.learnjwt.services;

import com.example.learnjwt.models.Role;
import com.example.learnjwt.models.User;
import com.example.learnjwt.repositories.UserRepository;
import com.example.learnjwt.request.LoginRequest;
import com.example.learnjwt.request.RegisterRequest;
import com.example.learnjwt.response.JwtResponse;
import com.example.learnjwt.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements  IUserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JwtResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("Invalid email or password");
        }
        User existingUser = user.get();
        if(!passwordEncoder.matches(password, existingUser.getPassword())){
            throw new UsernameNotFoundException("Invalid email or password");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                email, password
        );
        authenticationManager.authenticate(authenticationToken);
        String token = jwtService.generateToken(existingUser);
        return JwtResponse.builder()
                .token(token)
                .build();
    }

    @Override
    public void register(RegisterRequest registerRequest) {
        User user = User.builder()
                .email(registerRequest.getEmail())
                .firstName(registerRequest.getFirstName())
                .lastName(registerRequest.getLastName())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .build();
        Role role = Role.builder()
                .name("user")
                .user(user)
                .build();
        user.setRoleList(List.of(role));
        userRepository.save(user);
    }
}
