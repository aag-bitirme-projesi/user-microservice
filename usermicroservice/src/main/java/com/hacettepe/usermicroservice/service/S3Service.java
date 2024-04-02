package com.hacettepe.usermicroservice.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;

@Service
public class S3Service {

    private final AmazonS3 s3Client;
    private static final String CV_BUCKET_NAME = "user-cv-storage";

    public S3Service(@Value("${aws.access.key}") String accessKey,
                     @Value("${aws.secret.key}") String secretKey,
                     @Value("${aws.region}") String region) {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();
    }

    public String uploadCV(String keyname, MultipartFile file) throws IOException {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getInputStream().available());
        metadata.setContentType(file.getContentType());
        metadata.addUserMetadata("filename", file.getOriginalFilename());

        this.s3Client.putObject(CV_BUCKET_NAME, keyname, file.getInputStream(), metadata);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(CV_BUCKET_NAME, keyname)
                        .withMethod(HttpMethod.GET);

        URL url = this.s3Client.generatePresignedUrl(generatePresignedUrlRequest);

        return url.toString();
    }

}
