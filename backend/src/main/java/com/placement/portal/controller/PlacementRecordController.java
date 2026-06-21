package com.placement.portal.controller;

import com.placement.portal.dto.PlacementRecordDto;
import com.placement.portal.service.PlacementRecordService;
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
@RequestMapping("/api/placement-records")
@RequiredArgsConstructor
@Tag(name = "Placement Record Module", description = "CRUD APIs for Placement Records")
@SecurityRequirement(name = "bearerAuth")
public class PlacementRecordController {

    private final PlacementRecordService recordService;

    @PostMapping
    @Operation(summary = "Publish a new placement record manually")
    public ResponseEntity<PlacementRecordDto> createRecord(@Valid @RequestBody PlacementRecordDto recordDto) {
        return new ResponseEntity<>(recordService.createRecord(recordDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get placement record details by ID")
    public ResponseEntity<PlacementRecordDto> getRecordById(@PathVariable Long id) {
        return ResponseEntity.ok(recordService.getRecordById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing placement record details")
    public ResponseEntity<PlacementRecordDto> updateRecord(@PathVariable Long id, @Valid @RequestBody PlacementRecordDto recordDto) {
        return ResponseEntity.ok(recordService.updateRecord(id, recordDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a placement record entry by ID")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        recordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/student/{studentId}")
    @Operation(summary = "Get placement records of a specific student")
    public ResponseEntity<List<PlacementRecordDto>> getRecordsByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(recordService.getRecordsByStudentId(studentId));
    }

    @GetMapping
    @Operation(summary = "Get list of all recorded placements")
    public ResponseEntity<List<PlacementRecordDto>> getAllRecords() {
        return ResponseEntity.ok(recordService.getAllRecords());
    }
}
