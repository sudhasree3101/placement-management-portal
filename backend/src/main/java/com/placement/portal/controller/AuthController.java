package com.placement.portal.controller;

import com.placement.portal.dto.*;
import com.placement.portal.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication Module", description = "APIs for user registration, authentication, and password management")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register/student")
    @Operation(summary = "Register a new student account")
    public ResponseEntity<AuthenticationResponse> registerStudent(@Valid @RequestBody StudentRegisterRequest request) {
        return new ResponseEntity<>(authService.registerStudent(request), HttpStatus.CREATED);
    }

    @PostMapping("/register/recruiter")
    @Operation(summary = "Register a new recruiter account")
    public ResponseEntity<AuthenticationResponse> registerRecruiter(@Valid @RequestBody RecruiterRegisterRequest request) {
        return new ResponseEntity<>(authService.registerRecruiter(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate user credentials and return JWT token")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Request a password reset token")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request);
        return ResponseEntity.ok("Password reset instructions sent to email");
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset password using the reset token")
    public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return ResponseEntity.ok("Password reset successfully");
    }
}
