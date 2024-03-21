package com.hacettepe.usermicroservice.repository;

import com.hacettepe.usermicroservice.model.Model;
import com.hacettepe.usermicroservice.model.PaymentInfo;
import com.hacettepe.usermicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IPaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {
    @Query(value = "UPDATE payment_infos SET stripe_token = :stripeTokens WHERE card_number = :cardNumber", nativeQuery = true)
    void addStripeToken(int cardNumber, String stripeToken);

    PaymentInfo findPaymentInfoByCardNumber(int cardNumber);
}
