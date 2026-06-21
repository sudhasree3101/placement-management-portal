package com.placement.portal.controller;

import com.placement.portal.dto.ApplicationDto;
import com.placement.portal.dto.ApplicationStatusRequest;
import com.placement.portal.service.ApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
@RequiredArgsConstructor
@Tag(name = "Application Module", description = "APIs for job drive application lifecycle management")
@SecurityRequirement(name = "bearerAuth")
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping("/apply")
    @Operation(summary = "Submit a student application for a placement drive")
    public ResponseEntity<ApplicationDto> applyForDrive(@Valid @RequestBody ApplicationDto applicationDto) {
        return new ResponseEntity<>(applicationService.applyForDrive(applicationDto), HttpStatus.CREATED);
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get all drive applications of a specific student")
    public ResponseEntity<List<ApplicationDto>> getApplicationsByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(applicationService.getApplicationsByStudentId(studentId));
    }

    @GetMapping("/drive/{driveId}")
    @Operation(summary = "Get all student applications for a specific placement drive")
    public ResponseEntity<List<ApplicationDto>> getApplicationsByDriveId(@PathVariable Long driveId) {
        return ResponseEntity.ok(applicationService.getApplicationsByDriveId(driveId));
    }

    @PutMapping("/status")
    @Operation(summary = "Update status of a drive application (APPLIED, SHORTLISTED, REJECTED, SELECTED)")
    public ResponseEntity<ApplicationDto> updateApplicationStatus(@Valid @RequestBody ApplicationStatusRequest request) {
        return ResponseEntity.ok(applicationService.updateApplicationStatus(request));
    }

    @GetMapping
    @Operation(summary = "Get list of all applications")
    public ResponseEntity<List<ApplicationDto>> getAllApplications() {
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get details of an application by ID")
    public ResponseEntity<ApplicationDto> getApplicationById(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }
}
