package dev.comakeit.potholechallenge.controllers;

import dev.comakeit.potholechallenge.entity.Cluster;
import dev.comakeit.potholechallenge.entity.Record;
import dev.comakeit.potholechallenge.entity.User;
import dev.comakeit.potholechallenge.repositories.ClustersRepository;
import dev.comakeit.potholechallenge.repositories.RecordsRepository;
import dev.comakeit.potholechallenge.repositories.UsersRepository;
import dev.comakeit.potholechallenge.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    RecordsRepository recordsRepository;

    @Autowired
    ClustersRepository clustersRepository;

    @PostMapping("contractor/approve/{username}")
    private User approveContractor(@PathVariable String username) {
        User user = usersRepository.findUserByUsername(username);
        System.out.println(user);
        if (!user.getRoles().contains("CONTRACTOR"))
            user.setRoles(user.getRoles() + "," + "CONTRACTOR");

        return usersRepository.save(user);
    }

    @GetMapping("contractor/applications")
    public List<User> getContractorApplications() {
        return usersRepository.getAllContractorApplications();
    }

    @GetMapping("users")
    private List<User> getAllUsers() {
        return usersRepository.findAll();
    }

    @GetMapping("records")
    private List<Record> getAllRecords() {
        recordsRepository.findAll().forEach(record -> {
            System.out.println(record.getCluster());
        });
        return recordsRepository.findAll();
    }

    @GetMapping("clusters")
    private List<Cluster> getAllClusters() {
        List<Cluster> clusters = new ArrayList<>();
        for (Cluster c : clustersRepository.findAll()) {
            clusters.add(new Cluster(c.getZipcode(),
                    c.getContractorId(),
                    c.getStatus(),
                    recordsRepository.findRecordsByzipcode(c.getZipcode())));
        }
        return clusters;
    }

    @GetMapping("/user/{uuid}")
    public User findUser(@PathVariable String uuid) {
        return usersRepository.findUserByuserId(UUID.fromString(uuid));
    }

    @DeleteMapping("user/{username}")
    public Boolean deleteUser(@PathVariable String username) {
        User user = usersRepository.findUserByUsername(username);
        usersRepository.delete(user);
        return true;
    }
}
