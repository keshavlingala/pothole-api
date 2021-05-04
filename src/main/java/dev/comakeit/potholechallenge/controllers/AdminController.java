package dev.comakeit.potholechallenge.controllers;

import dev.comakeit.potholechallenge.entity.Bid;
import dev.comakeit.potholechallenge.entity.Cluster;
import dev.comakeit.potholechallenge.entity.Record;
import dev.comakeit.potholechallenge.entity.User;
import dev.comakeit.potholechallenge.repositories.BidsRepository;
import dev.comakeit.potholechallenge.repositories.ClustersRepository;
import dev.comakeit.potholechallenge.repositories.RecordsRepository;
import dev.comakeit.potholechallenge.repositories.UsersRepository;
import dev.comakeit.potholechallenge.services.BidsService;
import dev.comakeit.potholechallenge.services.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    @Autowired
    BidsRepository bidsRepository;

    @Autowired
    BidsService bidsService;

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
        return recordsRepository.findAll();
    }

    @GetMapping("contracts")
    private List<Cluster> getAllClusters() {
        return clustersRepository
                .findAll().stream()
                .map(c -> new Cluster(c.getZipcode(), c.getContractorId(), c.getStatus(),
                        recordsRepository.findRecordsByzipcode(c.getZipcode()))).collect(Collectors.toList());
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

    @GetMapping("bids")
    public List<Bid> getAllBids() {
        return bidsRepository.getPendingBids();
    }

    @GetMapping("clusters")
    private List<Cluster> getAllContracts() {
        return clustersRepository.findAll()
                .stream()
                .map(cluster -> new Cluster(cluster.getZipcode(), cluster.getContractorId(), cluster.getStatus(),
                        recordsRepository.findRecordsByzipcode(cluster.getZipcode()))).collect(Collectors.toList());
    }

    @PostMapping("bid/approve/{bidid}")
    public Bid approveBid(@PathVariable("bidid") String bidid) {
        return bidsService.approveBid(UUID.fromString(bidid));
    }
}
