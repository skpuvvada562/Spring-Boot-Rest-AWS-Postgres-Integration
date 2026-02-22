package com.spring.boot.rest.db.service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.boot.rest.db.config.AwsS3Config;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class AwsS3Service {

	@Value("${s3.bucket.name}")
	private String bucketName;
	
	@Autowired
	AwsS3Config s3Config;
	
	
	public String uploadFile(MultipartFile file) throws IOException {

        String fileName = file.getOriginalFilename();

        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .contentType(file.getContentType())
                .build();

        s3Config.getS3Client().putObject(
                putObjectRequest,
                RequestBody.fromBytes(file.getBytes())
        );

        return fileName;
    }
	
	public List<String> listOfFilesinS3() {
		ListObjectsV2Response response = s3Config.getS3Client()
				.listObjectsV2(ListObjectsV2Request.builder().bucket(bucketName).build());
		List<String> fileNames = response.contents().stream().map(obj -> obj.key()).toList();
		return fileNames;
	}
	
	public void deleteFileFromS3(String fileName) {
		s3Config.getS3Client().deleteObject(builder -> builder.bucket(bucketName).key(fileName));
	}
	
	public byte[] downloadFileFromS3(String fileName) {
		return s3Config.getS3Client().getObjectAsBytes(builder -> builder.bucket(bucketName).key(fileName))
				.asByteArray();
	}
	
}
