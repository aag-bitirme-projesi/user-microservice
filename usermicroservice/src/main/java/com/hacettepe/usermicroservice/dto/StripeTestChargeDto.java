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
public class StripeTestChargeDto {
    private double amount;
    private Map<String, String> additionalInfo = Map.ofEntries(
            Map.entry("ID_TAG", "1234567890")
    );
}
