package com.placement.portal.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewRoundDto {

    private Long id;

    @NotNull(message = "Application ID is required")
    private Long applicationId;

    @NotBlank(message = "Round name is required")
    private String roundName;

    private String status;
    private String feedback;

    @NotNull(message = "Scheduled date is required")
    private LocalDateTime scheduledDate;
}
