package com.placement.portal.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "placement_records")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlacementRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String jobRole;

    private Double packageAmount;
    private LocalDate joiningDate;
}
