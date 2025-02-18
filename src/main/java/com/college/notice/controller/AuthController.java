package com.college.notice.controller;


import com.college.notice.entity.AuthUser;
import com.college.notice.entity.LoginRequest;
import com.college.notice.jwt.AuthUserService;
import com.college.notice.jwt.JwtUtility;
import com.college.notice.repository.AuthUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtility jwtUtility;

    @Autowired
    private AuthUserService authUserService;

    @Autowired
    private AuthUserRepository authUserRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        logger.info("Login attempt for user: {}", loginRequest.getEmail());

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(),
                            loginRequest.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtility.generateTokenFromUsername(
                    ((UserDetails) authentication.getPrincipal()));

            logger.info("User {} successfully authenticated, JWT generated.", loginRequest.getEmail());
            return ResponseEntity.ok(jwt);

        } catch (Exception e) {
            logger.error("Authentication failed for user {}: {}", loginRequest.getEmail(), e.getMessage());
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody AuthUser authUser) {
        // Check if the user already exists
        if (authUserRepository.findByEmail(authUser.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already in use");
        }

        // Hash the password before storing
        String hashedPassword = passwordEncoder.encode(authUser.getHashedPassword());
        AuthUser newUser = new AuthUser(null, authUser.getEmail(), authUser.getName(), hashedPassword, authUser.getRole());

        // Save to the repository
        AuthUser savedUser = authUserRepository.save(newUser);
        return ResponseEntity.ok(savedUser);
    }
}
