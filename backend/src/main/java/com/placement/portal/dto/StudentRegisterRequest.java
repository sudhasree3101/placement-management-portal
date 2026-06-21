package com.placement.portal.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentRegisterRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 50, message = "Password must be at least 6 characters")
    private String password;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone must be a valid number between 10 and 15 digits")
    private String phone;

    @NotBlank(message = "Department is required")
    private String department;

    @NotNull(message = "CGPA is required")
    @DecimalMin(value = "0.0", message = "CGPA cannot be less than 0.0")
    @DecimalMax(value = "10.0", message = "CGPA cannot be more than 10.0")
    private Double cgpa;

    private String skills;

    @NotNull(message = "Graduation year is required")
    @Min(value = 2000, message = "Graduation year must be valid")
    private Integer graduationYear;

    private String resumeUrl;
}
