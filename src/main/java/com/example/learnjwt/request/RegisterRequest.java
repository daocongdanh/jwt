package com.example.learnjwt.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegisterRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String password;

}
