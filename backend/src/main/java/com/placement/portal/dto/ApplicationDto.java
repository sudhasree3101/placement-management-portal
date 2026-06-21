package com.placement.portal.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDto {

    private Long id;
    private Long studentId;
    private Long placementDriveId;
    
    private String studentName;
    private String studentEmail;
    private String companyName;
    private String jobRole;
    
    private String status;
    private LocalDateTime appliedDate;
}
