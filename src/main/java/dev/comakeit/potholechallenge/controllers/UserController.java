package dev.comakeit.potholechallenge.controllers;

import dev.comakeit.potholechallenge.entity.Cluster;
import dev.comakeit.potholechallenge.entity.Record;
import dev.comakeit.potholechallenge.entity.User;
import dev.comakeit.potholechallenge.models.ClusterStatus;
import dev.comakeit.potholechallenge.models.ContractorApplication;
import dev.comakeit.potholechallenge.repositories.ClustersRepository;
import dev.comakeit.potholechallenge.repositories.RecordsRepository;
import dev.comakeit.potholechallenge.repositories.UsersRepository;
import dev.comakeit.potholechallenge.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    ClustersRepository clustersRepository;

    @Autowired
    StorageService storageService;

    @Autowired
    RecordsRepository recordsRepository;

    @GetMapping
    public String welcomeUser() {
        return "Welcome User";
    }

    @GetMapping("whoami")
    public User whoami() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @PostMapping("contractor/apply")
    public User contractorApply(@RequestBody ContractorApplication application) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(user);
        user.setLicence(application.getLicence());
        return usersRepository.save(user);
//        usersRepository.findUserByUsername()
    }

    @GetMapping("record/upload")
    public ResponseEntity<String> getrecordUpload() {
        return new ResponseEntity<>("Request Not Allowed", HttpStatus.METHOD_NOT_ALLOWED);
    }

    @GetMapping("record/myrecords")
    public List<Record> getMyRecords() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return recordsRepository.findRecordsByuserId(user.getUserId());
    }


    @PostMapping("record/upload")
    public Record uploadRecord(@RequestParam("file") MultipartFile file,
                               @RequestParam("description") String description,
                               @RequestParam("lat") Double lat,
                               @RequestParam("lng") Double lng,
                               @RequestParam("zipcode") String zipcode) {
        String imgUrl = storageService.uploadFile(file);
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Record record = recordsRepository
                .save(new Record(user.getUserId(), zipcode, lat, lng, description, imgUrl));
        if (!clustersRepository.existsByzipcode(record.getZipcode()))
            clustersRepository.save(new Cluster(record.getZipcode(), null, ClusterStatus.UNASSIGNED));
        return record;
    }
}
