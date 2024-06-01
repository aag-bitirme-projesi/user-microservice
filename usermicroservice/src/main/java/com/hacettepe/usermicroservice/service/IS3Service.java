package com.hacettepe.usermicroservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IS3Service {

    public void uploadProfilePicture(String keyname, MultipartFile file) throws IOException;

    public String getProfilePicture(String keyname);

    public void uploadCV(String keyname, MultipartFile file) throws IOException;

    public  String getCV(String keyname);
//
//    public String uploadModel(String keyname, MultipartFile file) throws IOException;

    public void deleteModel(String keyname);

}
