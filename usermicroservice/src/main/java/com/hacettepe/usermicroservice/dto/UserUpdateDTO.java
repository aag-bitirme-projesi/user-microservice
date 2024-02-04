package com.hacettepe.usermicroservice.dto;

import com.hacettepe.usermicroservice.model.PaymentInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDTO {
    private String username;
    private String email;
    private String oldPassword;
    private String password;
    private String cv;
    private String github;
    private PaymentInfoDTO paymentInfo;
}
