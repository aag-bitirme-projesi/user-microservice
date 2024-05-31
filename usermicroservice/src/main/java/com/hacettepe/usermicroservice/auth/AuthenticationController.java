package com.hacettepe.usermicroservice.auth;

import com.hacettepe.usermicroservice.exception.EmailUsedException;
import com.hacettepe.usermicroservice.exception.UserExistsException;
import com.hacettepe.usermicroservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request)  {
        try {
            return ResponseEntity.ok(authService.signUp(request));
        } catch(UserExistsException e) {
            return ResponseEntity.badRequest().body("Username already used.");
        } catch(EmailUsedException e) {
            return ResponseEntity.badRequest().body("Email already used.");
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.signIn(request));
    }
}
