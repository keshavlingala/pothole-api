package dev.comakeit.potholechallenge.controllers;

import dev.comakeit.potholechallenge.entity.Cluster;
import dev.comakeit.potholechallenge.entity.Record;
import dev.comakeit.potholechallenge.repositories.ClustersRepository;
import dev.comakeit.potholechallenge.repositories.RecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contractor")
public class ContractorController {
    @Autowired
    ClustersRepository clustersRepository;

    @Autowired
    RecordsRepository recordsRepository;

    @GetMapping
    public String welcomeContractor() {
        return "Welcome Contractor";
    }

    @GetMapping("clusters")
    List<List<Record>> getClusters() {
        return clustersRepository.findAll().stream().map(cluster -> {
            return recordsRepository.findRecordsByzipcode(cluster.getZipcode());
        }).collect(Collectors.toList());

    }
}
