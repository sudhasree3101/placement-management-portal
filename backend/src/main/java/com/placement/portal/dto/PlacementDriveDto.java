package com.placement.portal.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlacementDriveDto {

    private Long id;

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Job role is required")
    private String jobRole;

    @NotNull(message = "Package offered is required")
    @Positive(message = "Package offered must be positive")
    private Double packageOffered;

    @NotBlank(message = "Eligibility criteria is required")
    private String eligibilityCriteria;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Drive date is required")
    private LocalDate driveDate;

    @NotNull(message = "Registration deadline is required")
    private LocalDate registrationDeadline;

    @NotBlank(message = "Job description is required")
    @Size(max = 2000, message = "Job description cannot exceed 2000 characters")
    private String jobDescription;
}
