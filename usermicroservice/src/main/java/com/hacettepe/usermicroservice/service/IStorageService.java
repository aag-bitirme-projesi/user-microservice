package com.hacettepe.usermicroservice.service;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {

    public String uploadCv(MultipartFile pdfFile);
}
