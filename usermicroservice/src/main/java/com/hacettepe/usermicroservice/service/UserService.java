package com.hacettepe.usermicroservice.service;

import com.hacettepe.usermicroservice.model.User;
import com.hacettepe.usermicroservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findById(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User updateUser(User new_user) throws Exception {
        User user = userRepository.findById(new_user.getUsername()).orElse(null);
        if (user == null) {
            // TODO USER NOT FOUND
            throw new Exception();
        }

        // TODO UPDATE EMAIL
        // TODO UPDATE PASSWORD
        // TODO UPDATE CV

        // TODO UPDATE GITHUB LINK
        if (new_user.getGithub() != null)
            user.setGithub(new_user.getGithub());

        // TODO UPDATE PAYMENT INFO


        return userRepository.save(user);
    }
}
