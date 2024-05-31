package com.hacettepe.usermicroservice.auth;

import com.hacettepe.usermicroservice.config.JwtService;
import com.hacettepe.usermicroservice.exception.EmailUsedException;
import com.hacettepe.usermicroservice.exception.UserExistsException;
import com.hacettepe.usermicroservice.model.User;
import com.hacettepe.usermicroservice.repository.IUserRepository;
import com.hacettepe.usermicroservice.utils.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @ExceptionHandler({UserExistsException.class, EmailUsedException.class})
    public AuthenticationResponse signUp(SignUpRequest request) throws UserExistsException, EmailUsedException {
        boolean emailExists = userRepository.findByEmail(request.getEmail()).isPresent();
        boolean usernameExists = userRepository.findByUsername(request.getUsername()).isPresent();

        if(usernameExists) {
            throw new UserExistsException("This username has been used.");
        }

        if(emailExists) {
            throw new EmailUsedException("This email has been used.");
        }

        var user = User.builder()
                .username(request.getUsername())
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        log.info("HEREBACKEND6666");
        log.info(String.valueOf(jwtToken));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthenticationResponse signIn(AuthenticationRequest request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(); //todo handle the exception

        var jwtToken = jwtService.generateToken(user);
        log.info("HEREBACKEND6666");
        log.info(String.valueOf(jwtToken));
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
