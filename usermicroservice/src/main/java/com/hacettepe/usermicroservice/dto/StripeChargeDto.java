package com.hacettepe.usermicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StripeChargeDto {
    private String token;
    private double amount;
    private String chargeId;
    private Map<String, String> additionalInfo = new HashMap<>();
}
