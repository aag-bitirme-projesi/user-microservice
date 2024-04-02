package com.hacettepe.usermicroservice.controller;

import com.hacettepe.usermicroservice.dto.UserUpdateDTO;
import com.hacettepe.usermicroservice.exception.UserNotFoundException;
import com.hacettepe.usermicroservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hacettepe.usermicroservice.exception.EmailSendingException;
import com.hacettepe.usermicroservice.service.PasswordResetTokenService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;


@RestController
//@Secured(SecurityUtils.ROLE_USER)
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private  PasswordResetTokenService passwordResetTokenService;

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public ResponseEntity<String> securedHome() {
        return ResponseEntity.ok("secured endpoint home");
    }

    @PostMapping("/update-user")
    public ResponseEntity<?> updateUser(@ModelAttribute UserUpdateDTO userUpdateDTO) {
        try {
            userService.updateUser(userUpdateDTO);
            return ResponseEntity.ok("Update Sent Successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body("User not found.");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error during cv upload.");
        }

    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> emailMap) {
        try {
            String token = passwordResetTokenService.createToken(emailMap.get("email"));
            return ResponseEntity.ok("Password reset email sent successfully. Check your email.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body("User not found.");
        } catch (EmailSendingException e) {
            return ResponseEntity.badRequest().body("Error sending password reset email.");
        }
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable String token, @RequestBody Map<String, String> passwordMap) {
        if (!passwordResetTokenService.validatePasswordResetToken(token))
            return ResponseEntity.badRequest().body("Token is invalid.");

        passwordResetTokenService.resetPassword(token, passwordMap.get("password"));
        return ResponseEntity.ok("Password reset successfully");
    }
}
