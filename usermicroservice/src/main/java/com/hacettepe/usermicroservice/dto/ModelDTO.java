package com.hacettepe.usermicroservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModelDTO {
    private MultipartFile file;
    private String name;
    private String modelLink;
    private double price;
    private String description;
}
