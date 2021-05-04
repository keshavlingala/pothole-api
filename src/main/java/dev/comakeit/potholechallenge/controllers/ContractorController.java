package dev.comakeit.potholechallenge.controllers;

import dev.comakeit.potholechallenge.entity.Bid;
import dev.comakeit.potholechallenge.entity.Cluster;
import dev.comakeit.potholechallenge.entity.User;
import dev.comakeit.potholechallenge.exceptions.UnAuthorizedException;
import dev.comakeit.potholechallenge.models.BidRequest;
import dev.comakeit.potholechallenge.models.ClusterStatus;
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
    private List<Cluster> getOpenClusters() {
        return clustersRepository
                .findOpenContracts().stream()
                .map(c -> new Cluster(c.getZipcode(), c.getContractorId(), c.getStatus(),
                        recordsRepository.findRecordsByzipcode(c.getZipcode()))).collect(Collectors.toList());
    }

    @GetMapping("bids")
    private List<Bid> getMyBids() {
        User contractor = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return bidsRepository.findBidsBycontractorId(contractor.getUserId());
    }

    @GetMapping("mycontracts")
    private List<Cluster> getMyContracts() {
        User contractor = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return clustersRepository
                .findClustersBycontractorId(contractor.getUserId())
                .stream().map(cluster ->
                        new Cluster(cluster.getZipcode(), cluster.getContractorId(), cluster.getStatus(),
                                recordsRepository.findRecordsByzipcode(cluster.getZipcode()))).collect(Collectors.toList());
    }

    @GetMapping("bid/{zipcode}")
    private Bid isAvailable(@PathVariable("zipcode") String zipcode) {
        User contractor = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return bidsRepository.findBidByUser(contractor.getUserId(), zipcode);
    }

    @PutMapping("contract/{zipcode}")
    private Cluster updateStatus(@PathVariable("zipcode") String zipcode, @RequestParam("status") String status) throws UnAuthorizedException {
        User contractor = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var cluster = clustersRepository.findClusterByzipcode(zipcode);
        if (cluster.getContractorId() == null || !cluster.getContractorId().equals(contractor.getUserId()))
            throw new UnAuthorizedException();
        cluster.setStatus(ClusterStatus.valueOf(status));
        clustersRepository.save(cluster);
        return cluster;
    }

    @PostMapping("bid/apply/{zipcode}")
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
