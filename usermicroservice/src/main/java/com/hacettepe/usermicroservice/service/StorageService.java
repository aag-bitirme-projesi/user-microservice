package com.hacettepe.usermicroservice.service;

import io.supabase.StorageClient;
import io.supabase.api.IStorageFileAPI;
import io.supabase.data.bucket.CreateBucketResponse;
import io.supabase.data.file.FileDownloadOption;
import io.supabase.data.file.FilePathResponse;
import io.supabase.data.file.FileTransformOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class StorageService {
    @Value("${supabase.url}")
    private String supabaseUrl;

    @Value("${supabase.anon}")
    private String supabaseServiceToken;

    public String uploadCv(MultipartFile pdfFile) {
        try {
            String filename = pdfFile.getOriginalFilename();
            StorageClient storageClient = new StorageClient(supabaseServiceToken, supabaseUrl);
            CompletableFuture<CreateBucketResponse> res = storageClient.createBucket("user_cv");

            CreateBucketResponse bucketResponse = res.get();
            IStorageFileAPI fileAPI = storageClient.from(bucketResponse.getName());

            File convertedFile = convertMultipartFile2File(pdfFile);
            String destinationPath  = "user_cv/" + filename;
            FilePathResponse response = fileAPI.upload(destinationPath, convertedFile).get();

            return fileAPI.getPublicUrl(destinationPath, new FileDownloadOption(false), null).getPublicUrl();
        } catch (ExecutionException | InterruptedException | IOException e) {
            throw new RuntimeException("Failed to upload PDF file");
        }
    }

    private File convertMultipartFile2File(MultipartFile multipartFile) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(convertedFile);
        return convertedFile;
    }

}
