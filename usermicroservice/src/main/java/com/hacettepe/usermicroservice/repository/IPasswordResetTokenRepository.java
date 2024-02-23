package com.hacettepe.usermicroservice.repository;

import com.hacettepe.usermicroservice.auth.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
}
