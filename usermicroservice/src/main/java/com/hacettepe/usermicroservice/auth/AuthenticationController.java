package com.hacettepe.usermicroservice.auth;

import com.hacettepe.usermicroservice.exception.EmailUsedException;
import com.hacettepe.usermicroservice.exception.UserExistsException;
import com.hacettepe.usermicroservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authService;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequest request)  {
        try {
            return ResponseEntity.ok(authService.signUp(request));
        } catch(UserExistsException e) {
            return ResponseEntity.badRequest().body("Username already used.");
        } catch(EmailUsedException e) {
            return ResponseEntity.badRequest().body("Email already used.");
        }
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthenticationResponse> signIn(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.signIn(request));
    }
}
