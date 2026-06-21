package com.placement.portal.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlacementRecordDto {

    private Long id;

    @NotNull(message = "Student ID is required")
    private Long studentId;

    private String studentName;
    private String studentEmail;

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Job role is required")
    private String jobRole;

    @NotNull(message = "Package amount is required")
    @Positive(message = "Package amount must be positive")
    private Double packageAmount;

    @NotNull(message = "Joining date is required")
    private LocalDate joiningDate;
}
