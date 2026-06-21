package com.placement.portal.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recruiters")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String hrName;

    @Column(unique = true, nullable = false)
    private String email;

    private String phone;
    private String website;
    private String industry;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
}
