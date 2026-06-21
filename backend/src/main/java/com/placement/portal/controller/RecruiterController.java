package com.placement.portal.controller;

import com.placement.portal.dto.RecruiterDto;
import com.placement.portal.service.RecruiterService;
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
@RequestMapping("/api/recruiters")
@RequiredArgsConstructor
@Tag(name = "Recruiter Module", description = "CRUD APIs for Recruiters")
@SecurityRequirement(name = "bearerAuth")
public class RecruiterController {

    private final RecruiterService recruiterService;

    @PostMapping
    @Operation(summary = "Create a new recruiter profile")
    public ResponseEntity<RecruiterDto> createRecruiter(@Valid @RequestBody RecruiterDto recruiterDto) {
        return new ResponseEntity<>(recruiterService.createRecruiter(recruiterDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get recruiter details by ID")
    public ResponseEntity<RecruiterDto> getRecruiterById(@PathVariable Long id) {
        return ResponseEntity.ok(recruiterService.getRecruiterById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing recruiter profile")
    public ResponseEntity<RecruiterDto> updateRecruiter(@PathVariable Long id, @Valid @RequestBody RecruiterDto recruiterDto) {
        return ResponseEntity.ok(recruiterService.updateRecruiter(id, recruiterDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a recruiter profile by ID")
    public ResponseEntity<Void> deleteRecruiter(@PathVariable Long id) {
        recruiterService.deleteRecruiter(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get list of all registered recruiters")
    public ResponseEntity<List<RecruiterDto>> getAllRecruiters() {
        return ResponseEntity.ok(recruiterService.getAllRecruiters());
    }
}
