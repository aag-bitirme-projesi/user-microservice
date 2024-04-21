package com.hacettepe.usermicroservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IS3Service {

    public String uploadCV(String keyname, MultipartFile file) throws IOException;

}
