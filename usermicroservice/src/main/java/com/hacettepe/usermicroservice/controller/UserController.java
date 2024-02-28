package com.hacettepe.usermicroservice.controller;

import com.hacettepe.usermicroservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hacettepe.usermicroservice.exception.EmailSendingException;
import com.hacettepe.usermicroservice.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
//@Secured(SecurityUtils.ROLE_USER)
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private  PasswordResetTokenService passwordResetTokenService;

    @GetMapping("/home")
    public ResponseEntity<String> securedHome() {
        return ResponseEntity.ok("secured endpoint home");
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestParam("email") String email) {
        try {
            String token = passwordResetTokenService.createToken(email);
            return ResponseEntity.ok("Password reset email sent successfully. Check your email.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body("User not found.");
        } catch (EmailSendingException e) {
            return ResponseEntity.badRequest().body("Error sending password reset email.");
        }
    }

    @PostMapping("/resetPassword/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable String token, @RequestParam("newPassword") String newPassword) {
        if (!passwordResetTokenService.validatePasswordResetToken(token))
            return ResponseEntity.badRequest().body("Token is invalid.");

        passwordResetTokenService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password reset successfully");
    }
}
