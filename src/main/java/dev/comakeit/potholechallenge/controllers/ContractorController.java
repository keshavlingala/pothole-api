package dev.comakeit.potholechallenge.controllers;

import dev.comakeit.potholechallenge.entity.Bid;
import dev.comakeit.potholechallenge.entity.Cluster;
import dev.comakeit.potholechallenge.entity.User;
import dev.comakeit.potholechallenge.models.BidRequest;
import dev.comakeit.potholechallenge.repositories.BidsRepository;
import dev.comakeit.potholechallenge.repositories.ClustersRepository;
import dev.comakeit.potholechallenge.repositories.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contractor")
public class ContractorController {
    @Autowired
    ClustersRepository clustersRepository;

    @Autowired
    RecordsRepository recordsRepository;

    @Autowired
    BidsRepository bidsRepository;

    @GetMapping
    public String welcomeContractor() {
        return "Welcome Contractor";
    }

    @GetMapping("clusters")
    private List<Cluster> getAllClusters() {
        return clustersRepository
                .findAll().stream()
                .map(c -> new Cluster(c.getZipcode(), c.getContractorId(), c.getStatus(),
                        recordsRepository.findRecordsByzipcode(c.getZipcode()))).collect(Collectors.toList());
    }

    @PostMapping("cluster/{zipcode}/apply")
    private Bid applyBid(@RequestBody BidRequest bidRequest, @PathVariable("zipcode") String zipcode) {
        User contractor = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return bidsRepository.save(
                new Bid(contractor.getUserId(),
                        zipcode,
                        bidRequest.getBidAmount(),
                        bidRequest.getDescription())
        );
    }
}
