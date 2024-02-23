package com.hacettepe.usermicroservice.controller;

import com.hacettepe.usermicroservice.exception.UserNotFoundException;
import com.hacettepe.usermicroservice.model.User;
import com.hacettepe.usermicroservice.service.IUserService;
import com.hacettepe.usermicroservice.utils.EmailClient;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
//@Secured(SecurityUtils.ROLE_USER)
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;
    private final EmailClient emailClient;

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("hello world!");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(HttpServletRequest request,
                                                @RequestParam("email") String userEmail) {
        User user = userService.findUserByEmail(userEmail);

        if (user == null) {
            throw new UserNotFoundException("Email not found.");
        }

        String token = UUID.randomUUID().toString();
        //userService.createPasswordResetToken(user, token);

        emailClient.constructEmail(getAppUrl(request), token, userEmail);

        return ResponseEntity.ok("reset email is sent.");  // link is a url for changing password (ataberk)
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}
