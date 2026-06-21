package com.placement.portal.service;

import com.placement.portal.dto.*;

public interface AuthService {
    AuthenticationResponse registerStudent(StudentRegisterRequest request);
    AuthenticationResponse registerRecruiter(RecruiterRegisterRequest request);
    AuthenticationResponse login(LoginRequest request);
    void forgotPassword(ForgotPasswordRequest request);
    void resetPassword(ResetPasswordRequest request);
}
