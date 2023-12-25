package com.hacettepe.usermicroservice.controller;

import com.hacettepe.usermicroservice.model.PasswordResetToken;
import com.hacettepe.usermicroservice.model.User;
import com.hacettepe.usermicroservice.service.PasswordResetTokenService;
import com.hacettepe.usermicroservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private  PasswordResetTokenService passwordResetTokenService;

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {
        try {
            String token = passwordResetTokenService.createToken(email);
            return ResponseEntity.ok("Password reset email sent successfully. Check your email.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("User not found.");
        }
    }

    /*
    @GetMapping("/validateToken/{token}")
    public ResponseEntity<?> validateToken(@PathVariable String token) {
        if (passwordResetTokenService.validatePasswordResetToken(token))
            return ResponseEntity.ok("Token is valid.");
        return ResponseEntity.badRequest().body("Token is invalid.");
    }
    */

    @PostMapping("/resetPassword/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable String token, @RequestParam("newPassword") String newPassword) {
        if (!passwordResetTokenService.validatePasswordResetToken(token))
            return ResponseEntity.badRequest().body("Token is invalid.");

        passwordResetTokenService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password reset successfully");
    }
}
