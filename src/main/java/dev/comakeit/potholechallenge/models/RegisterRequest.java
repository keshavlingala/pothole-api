package dev.comakeit.potholechallenge.models;

import lombok.Data;

@Data
public class RegisterRequest {
    String username;
    String email;
    String password;
}
