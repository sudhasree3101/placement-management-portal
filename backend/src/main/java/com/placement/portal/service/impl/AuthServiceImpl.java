package com.placement.portal.service.impl;

import com.placement.portal.dto.*;
import com.placement.portal.entity.*;
import com.placement.portal.exception.DuplicateResourceException;
import com.placement.portal.exception.ResourceNotFoundException;
import com.placement.portal.exception.UnauthorizedException;
import com.placement.portal.repository.UserRepository;
import com.placement.portal.repository.StudentRepository;
import com.placement.portal.repository.RecruiterRepository;
import com.placement.portal.repository.NotificationRepository;
import com.placement.portal.security.JwtService;
import com.placement.portal.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final RecruiterRepository recruiterRepository;
    private final NotificationRepository notificationRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthenticationResponse registerStudent(StudentRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email is already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.STUDENT)
                .enabled(true)
                .build();

        Student student = Student.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .department(request.getDepartment())
                .cgpa(request.getCgpa())
                .skills(request.getSkills())
                .graduationYear(request.getGraduationYear())
                .resumeUrl(request.getResumeUrl())
                .placementStatus(PlacementStatus.UNPLACED)
                .user(user)
                .build();

        studentRepository.save(student);

        String token = jwtService.generateToken(user);

        Notification notification = Notification.builder()
                .recipientEmail(user.getEmail())
                .message("Welcome " + user.getName() + "! Your student registration was successful.")
                .isRead(false)
                .build();
        notificationRepository.save(notification);

        return AuthenticationResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .name(user.getName())
                .build();
    }

    @Override
    @Transactional
    public AuthenticationResponse registerRecruiter(RecruiterRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email is already registered");
        }

        User user = User.builder()
                .name(request.getHrName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.RECRUITER)
                .enabled(true)
                .build();

        Recruiter recruiter = Recruiter.builder()
                .companyName(request.getCompanyName())
                .hrName(request.getHrName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .website(request.getWebsite())
                .industry(request.getIndustry())
                .user(user)
                .build();

        recruiterRepository.save(recruiter);

        String token = jwtService.generateToken(user);

        Notification notification = Notification.builder()
                .recipientEmail(user.getEmail())
                .message("Welcome " + recruiter.getCompanyName() + "! Your recruiter registration was successful.")
                .isRead(false)
                .build();
        notificationRepository.save(notification);

        return AuthenticationResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .name(user.getName())
                .build();
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getEmail()));

        if (!user.isEnabled()) {
            throw new UnauthorizedException("User account is disabled");
        }

        String token = jwtService.generateToken(user);

        return AuthenticationResponse.builder()
                .token(token)
                .email(user.getEmail())
                .role(user.getRole().name())
                .name(user.getName())
                .build();
    }

    @Override
    @Transactional
    public void forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getEmail()));

        String mockToken = "RESET_" + System.currentTimeMillis();
        
        Notification notification = Notification.builder()
                .recipientEmail(user.getEmail())
                .message("Password reset requested. Use token: " + mockToken)
                .isRead(false)
                .build();
        notificationRepository.save(notification);

        log.info("Password reset token for {}: {}", user.getEmail(), mockToken);
    }

    @Override
    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.getEmail()));

        if (request.getToken() == null || request.getToken().trim().isEmpty()) {
            throw new UnauthorizedException("Invalid password reset token");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);

        Notification notification = Notification.builder()
                .recipientEmail(user.getEmail())
                .message("Your password has been successfully reset.")
                .isRead(false)
                .build();
        notificationRepository.save(notification);
    }
}
