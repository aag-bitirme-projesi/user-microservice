package com.hacettepe.usermicroservice.dto;

import com.hacettepe.usermicroservice.model.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDTO {
    private String name;
    private String username;
    private String email;
    private String oldPassword;
    private String password;
    private MultipartFile profilePicture;
    private MultipartFile cv;
    private String github;
    private MultipartFile profilePhoto;
}
