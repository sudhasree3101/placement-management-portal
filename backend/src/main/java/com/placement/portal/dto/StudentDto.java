package com.placement.portal.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Phone must be a valid number between 10 and 15 digits")
    private String phone;

    @NotBlank(message = "Department is required")
    private String department;

    @NotNull(message = "CGPA is required")
    @DecimalMin(value = "0.0")
    @DecimalMax(value = "10.0")
    private Double cgpa;

    private String skills;

    @NotNull(message = "Graduation year is required")
    @Min(value = 2000)
    private Integer graduationYear;

    private String resumeUrl;

    private String placementStatus;
}
