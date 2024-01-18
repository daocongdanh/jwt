package com.example.learnjwt.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(requests -> {
                    requests
                            .requestMatchers("/api/v1/users/register").permitAll()
                            .requestMatchers("/api/v1/users/login").permitAll()
                            .requestMatchers(HttpMethod.GET,"/api/v1/categories/**").hasRole("USER")
                            .requestMatchers(HttpMethod.GET,"/api/v1/categories").hasRole("USER")
                            .requestMatchers(HttpMethod.POST,"/api/v1/categories").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT,"/api/v1/categories").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE,"/api/v1/categories/**").hasRole("ADMIN")
                            .anyRequest().authenticated();
                });



        return http.build();
    }
}
