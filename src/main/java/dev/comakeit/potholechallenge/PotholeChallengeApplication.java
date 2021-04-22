package dev.comakeit.potholechallenge;

import dev.comakeit.potholechallenge.entity.User;
import dev.comakeit.potholechallenge.repositories.ClustersRepository;
import dev.comakeit.potholechallenge.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.UUID;

@SpringBootApplication
public class PotholeChallengeApplication {
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ClustersRepository clustersRepository;

    public static void main(String[] args) {
        SpringApplication.run(PotholeChallengeApplication.class, args);
    }

    @PostConstruct
    public void init() {
        System.out.println("Hello Inited");
        if (!usersRepository.existsByUsername("potholeadmin")) {
            usersRepository
                    .save(
                            new User(UUID.randomUUID(),
                                    "potholeadmin",
                                    passwordEncoder.encode("potholeadmin"),
                                    "keshavlingala@gmail.com",
                                    "USER,CONTRACTOR,ADMIN",
                                    null)
                    );
            System.out.println("Admin Created ");
        }
    }

}
