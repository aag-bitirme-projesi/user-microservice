package com.hacettepe.usermicroservice.repository;

import com.hacettepe.usermicroservice.model.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {

}
