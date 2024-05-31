package com.hacettepe.usermicroservice.repository;

import com.hacettepe.usermicroservice.model.Payment;
import com.hacettepe.usermicroservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPaymentRepository extends JpaRepository<Payment, Long>  {
    void deleteByUser(User user);
}
