package com.hacettepe.usermicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Secured(SecurityUtils.ROLE_USER)
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    @GetMapping("/hello")
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("hello world!");
    }
}
