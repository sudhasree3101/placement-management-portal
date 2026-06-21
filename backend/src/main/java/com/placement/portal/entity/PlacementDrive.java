package com.placement.portal.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "placement_drives")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlacementDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String jobRole;

    private Double packageOffered;
    private String eligibilityCriteria;
    private String location;
    private LocalDate driveDate;
    private LocalDate registrationDeadline;

    @Column(length = 2000)
    private String jobDescription;
}
