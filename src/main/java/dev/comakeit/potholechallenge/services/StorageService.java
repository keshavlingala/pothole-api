package dev.comakeit.potholechallenge.services;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class StorageService {
    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    private AmazonS3Client amazonS3;

    public String uploadFile(MultipartFile uploadFile) {
        File file = getFileFromMultipart(uploadFile);
        String fileName = System.currentTimeMillis() + "_" + uploadFile.getOriginalFilename();
        amazonS3.putObject(
                new PutObjectRequest(bucketName, fileName, file)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        file.delete();
        return amazonS3.getResourceUrl(bucketName, fileName);
    }

    private File getFileFromMultipart(MultipartFile multipart) {
        File file = new File(multipart.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipart.getBytes());
        } catch (FileNotFoundException e) {
            log.error("Error Converting File");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
