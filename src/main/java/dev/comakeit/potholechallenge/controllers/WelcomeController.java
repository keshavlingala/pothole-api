package dev.comakeit.potholechallenge.controllers;

import dev.comakeit.potholechallenge.entity.User;
import dev.comakeit.potholechallenge.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WelcomeController {
    @Autowired
    UsersRepository usersRepository;

//    @PostConstruct
//    public void init() {
//        usersRepository.save(
//                new User(UUID.randomUUID(),
//                        "keshav",
//                        "098098",
//                        "keshav@gmail.com",
//                        "USER,CONTRACTOR,ADMIN",
//                        null)
//        );
//    }


}
