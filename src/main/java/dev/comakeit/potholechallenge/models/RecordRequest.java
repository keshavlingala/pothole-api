package dev.comakeit.potholechallenge.models;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RecordRequest {
    private Double lat;
    private Double lng;
    private String description;
    private String zipCode;
    private MultipartFile file;
}
