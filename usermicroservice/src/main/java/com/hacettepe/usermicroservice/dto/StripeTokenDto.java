package com.hacettepe.usermicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StripeTokenDto {
    private long cardNumber;
    private int cvc;
    private int expMonth;
    private int expYear;
    private String token = null;
    private boolean success = false;
}
