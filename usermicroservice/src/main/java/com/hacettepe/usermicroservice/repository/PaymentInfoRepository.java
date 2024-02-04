package com.hacettepe.usermicroservice.repository;

import com.hacettepe.usermicroservice.model.PaymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentInfoRepository extends JpaRepository<PaymentInfo, Long> {

}
