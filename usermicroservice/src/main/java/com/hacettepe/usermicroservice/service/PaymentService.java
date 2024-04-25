package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.dto.PaymentInfoDTO;
import com.hacettepe.usermicroservice.model.PaymentInfo;
import com.hacettepe.usermicroservice.repository.IPaymentInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {
    private final IPaymentInfoRepository paymentInfoRepository;

    //eğer ekleme ekranındaysa bunu biz true veririz, ödeme esnasında yeni olanı kaydedip kaydetmemek müşteri seçimi
    public PaymentInfo addNewPaymentMethod(PaymentInfoDTO newPayment, boolean saveOrNot) {
        PaymentInfo paymentInfo = PaymentInfo.builder()
                .cardNumber(newPayment.getCardNumber())
                .expirationMonth(newPayment.getExpirationMonth())
                .expirationYear(newPayment.getExpirationYear())
                .cvc(newPayment.getCvc())
                .owner(newPayment.getOwner())
                .cardName(newPayment.getCardName())
                .build();

        if (saveOrNot) {
            paymentInfoRepository.save(paymentInfo);
        }

        return paymentInfo;
    }

    public PaymentInfo choosePaymentMethod(long paymentId) {
        return paymentInfoRepository.findById(paymentId).get();
    }

}
