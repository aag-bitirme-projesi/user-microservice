package com.hacettepe.usermicroservice.controller;

import com.hacettepe.usermicroservice.dto.PasswordResetDto;
import com.hacettepe.usermicroservice.dto.UserInfoDto;
import com.hacettepe.usermicroservice.dto.UserUpdateDTO;
import com.hacettepe.usermicroservice.exception.PasswordMatchException;
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
@RequestMapping("user/user")
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

    @GetMapping("/get-user")
    public ResponseEntity<?> getUserInfo(@RequestBody String email) {
        try {
            UserInfoDto userInfoDto = userService.getUser(email);
            return ResponseEntity.ok(userInfoDto);
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body("User not found.");
        }
    }

    @PostMapping("/update-user")
    public ResponseEntity<?> updateUser(@ModelAttribute UserUpdateDTO userUpdateDTO) {
        try {
            userService.updateUser(userUpdateDTO);
            return ResponseEntity.ok("Update Sent Successfully");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body("User not found.");
        } catch (PasswordMatchException e) {
            return ResponseEntity.badRequest().body("Old password does not match.");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error during file upload.");
        }
    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> forgotPassword(@RequestBody String email) {
        try {
            String token = passwordResetTokenService.createToken(email);
            return ResponseEntity.ok("Password reset email sent successfully. Check your email.");
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body("User not found.");
        } catch (EmailSendingException e) {
            return ResponseEntity.badRequest().body("Error sending password reset email.");
        }
    }

    @PostMapping("/reset-password/{token}")
    public ResponseEntity<?> resetPassword(@ModelAttribute PasswordResetDto passwordResetDto) {
        String token = passwordResetDto.getToken();
        String newPassword = passwordResetDto.getPassword();

        if (!passwordResetTokenService.validatePasswordResetToken(token))
            return ResponseEntity.badRequest().body("Token is invalid.");

        passwordResetTokenService.resetPassword(token, newPassword);
        return ResponseEntity.ok("Password reset successfully");
    }
}
