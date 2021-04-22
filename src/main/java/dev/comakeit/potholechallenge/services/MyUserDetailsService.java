package dev.comakeit.potholechallenge.services;

import dev.comakeit.potholechallenge.entity.User;
import dev.comakeit.potholechallenge.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    UsersRepository usersRepository;

    @Override
    public User loadUserByUsername(String s) throws UsernameNotFoundException {
        return usersRepository.findUserByUsername(s);
    }
}
