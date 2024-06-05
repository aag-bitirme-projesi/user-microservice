package com.hacettepe.usermicroservice.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.util.Date;

@Service
public class S3Service implements IS3Service {

    private final AmazonS3 s3Client;
    private static final String CV_BUCKET_NAME = "user-cv-storage";
    private static final String PROFILE_PICTURE_BUCKET_NAME = "user-pp-storage";
    private static final String MODEL_BUCKET_NAME = "model-storage";

    public S3Service(@Value("${aws.access.key}") String accessKey,
                     @Value("${aws.secret.key}") String secretKey,
                     @Value("${aws.region}") String region) {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        this.s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();
    }

    @Override
    public void uploadProfilePicture(String keyname, MultipartFile file) throws IOException{
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getInputStream().available());
        metadata.setContentType(file.getContentType());
        metadata.addUserMetadata("filename", file.getOriginalFilename());

        this.s3Client.putObject(PROFILE_PICTURE_BUCKET_NAME, keyname, file.getInputStream(), metadata);
    }

    @Override
    public String getProfilePicture(String keyname) {
        Date expirationDate = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        ResponseHeaderOverrides headerOverrides = new ResponseHeaderOverrides().withContentType("image/jpg");

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(PROFILE_PICTURE_BUCKET_NAME, keyname)
                        .withExpiration(expirationDate)
                        .withMethod(HttpMethod.GET)
                        .withResponseHeaders(headerOverrides);

        URL url = this.s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    @Override
    public void uploadCV(String keyname, MultipartFile file) throws IOException {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getInputStream().available());
        metadata.setContentType(file.getContentType());
        metadata.addUserMetadata("filename", file.getOriginalFilename());

        this.s3Client.putObject(CV_BUCKET_NAME, keyname, file.getInputStream(), metadata);
    }

    @Override
    public String getCV(String keyname) {
        Date expirationDate = new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
        ResponseHeaderOverrides headerOverrides = new ResponseHeaderOverrides().withContentType("application/pdf");

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(CV_BUCKET_NAME, keyname)
                        .withExpiration(expirationDate)
                        .withMethod(HttpMethod.GET)
                        .withResponseHeaders(headerOverrides);

        URL url = this.s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    public void deleteModel(String keyname) {
        this.s3Client.deleteObject(new DeleteObjectRequest(MODEL_BUCKET_NAME, keyname));
    }
}
