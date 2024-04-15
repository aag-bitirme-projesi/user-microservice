package com.hacettepe.usermicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentInfoDTO {
    private long cardNumber;
    private int cvc;
    private int expirationMonth;
    private int expirationYear;
    private String owner;
    private String cardName;
}
