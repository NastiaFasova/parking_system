package demo.parkingsystem.controller;

import demo.parkingsystem.dto.auth.JwtAuthenticationResponse;
import demo.parkingsystem.dto.auth.LoginRequestDto;
import demo.parkingsystem.dto.auth.RegisterDto;
import demo.parkingsystem.security.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/register")
    public JwtAuthenticationResponse signUp(@RequestBody RegisterDto request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/login")
    public JwtAuthenticationResponse signIn(@RequestBody LoginRequestDto request) {
        logger.info("controller");
        return authenticationService.signIn(request);
    }
}
