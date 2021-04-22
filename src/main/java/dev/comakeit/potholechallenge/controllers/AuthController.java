package dev.comakeit.potholechallenge.controllers;

import dev.comakeit.potholechallenge.entity.User;
import dev.comakeit.potholechallenge.exceptions.InvalidUserNamePasswordException;
import dev.comakeit.potholechallenge.exceptions.UserAlreadyExistsException;
import dev.comakeit.potholechallenge.models.AuthResponse;
import dev.comakeit.potholechallenge.models.LoginRequest;
import dev.comakeit.potholechallenge.models.RegisterRequest;
import dev.comakeit.potholechallenge.repositories.UsersRepository;
import dev.comakeit.potholechallenge.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/p")
public class AuthController {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("register")
    public User registerUser(@RequestBody RegisterRequest request) throws UserAlreadyExistsException {
        if (usersRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException(request.getUsername());
        } else
            return usersRepository.save(new User(UUID.randomUUID(), request.getUsername(), passwordEncoder.encode(request.getPassword()), request.getEmail(), "USER", null));
    }

    @PostMapping("login")
    public AuthResponse generateToken(@RequestBody LoginRequest loginRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
        } catch (Exception ex) {
            throw new InvalidUserNamePasswordException();
        }
        return new AuthResponse(jwtUtil.generateToken(loginRequest.getUsername()));
    }

}
