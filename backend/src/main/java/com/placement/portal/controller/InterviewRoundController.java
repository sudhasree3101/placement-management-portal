package com.placement.portal.controller;

import com.placement.portal.dto.InterviewRoundDto;
import com.placement.portal.service.InterviewRoundService;
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
@RequestMapping("/api/interviews")
@RequiredArgsConstructor
@Tag(name = "Interview Module", description = "CRUD APIs for Interview Rounds")
@SecurityRequirement(name = "bearerAuth")
public class InterviewRoundController {

    private final InterviewRoundService interviewService;

    @PostMapping
    @Operation(summary = "Schedule a new interview round for an application")
    public ResponseEntity<InterviewRoundDto> createRound(@Valid @RequestBody InterviewRoundDto roundDto) {
        return new ResponseEntity<>(interviewService.createRound(roundDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get interview round details by ID")
    public ResponseEntity<InterviewRoundDto> getRoundById(@PathVariable Long id) {
        return ResponseEntity.ok(interviewService.getRoundById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing interview round results or schedule")
    public ResponseEntity<InterviewRoundDto> updateRound(@PathVariable Long id, @Valid @RequestBody InterviewRoundDto roundDto) {
        return ResponseEntity.ok(interviewService.updateRound(id, roundDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel or delete an interview round by ID")
    public ResponseEntity<Void> deleteRound(@PathVariable Long id) {
        interviewService.deleteRound(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/application/{applicationId}")
    @Operation(summary = "Get all interview rounds for a specific job application")
    public ResponseEntity<List<InterviewRoundDto>> getRoundsByApplicationId(@PathVariable Long applicationId) {
        return ResponseEntity.ok(interviewService.getRoundsByApplicationId(applicationId));
    }
}
