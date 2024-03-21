package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.dto.PaymentInfoDTO;
import com.hacettepe.usermicroservice.model.PaymentInfo;

public interface IPaymentService {
    PaymentInfo addNewPaymentMethod(PaymentInfoDTO newPayment, boolean saveOrNot);
    PaymentInfo choosePaymentMethod(long paymentId);
}
