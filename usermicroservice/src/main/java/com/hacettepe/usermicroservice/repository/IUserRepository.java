package com.hacettepe.usermicroservice.repository;

import com.hacettepe.usermicroservice.model.User;
import com.hacettepe.usermicroservice.utils.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, String> {

    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    //void deleteUserByEmail(String email);
}
