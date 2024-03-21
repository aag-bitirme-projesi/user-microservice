package com.hacettepe.usermicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayRequestDto {
    private boolean addNewPayment;
    private PaymentInfoDTO paymentInfoDTO = null;
    private boolean savePayment;
}
