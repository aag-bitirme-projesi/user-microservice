package com.hacettepe.usermicroservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IS3Service {

    public String uploadCV(String keyname, MultipartFile file) throws IOException;

    public String uploadModel(String keyname, MultipartFile file) throws IOException;

    public void deleteModel(String keyname);

}
