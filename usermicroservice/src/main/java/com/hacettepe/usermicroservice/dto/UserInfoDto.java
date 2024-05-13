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
public class UserInfoDto {
    private String username;
    private String email;
    private String profilePicture;
    private String cv;
    private String github;
    private PaymentInfoDTO paymentInfo;
}
