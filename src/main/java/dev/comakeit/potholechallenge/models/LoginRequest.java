package dev.comakeit.potholechallenge.models;

import lombok.Data;

@Data
public class LoginRequest {
    String username;
    String password;
}
