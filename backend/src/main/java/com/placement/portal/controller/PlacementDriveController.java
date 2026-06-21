package com.placement.portal.controller;

import com.placement.portal.dto.PlacementDriveDto;
import com.placement.portal.service.PlacementDriveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drives")
@RequiredArgsConstructor
@Tag(name = "Placement Drive Module", description = "CRUD APIs for Placement Drives with Pagination")
@SecurityRequirement(name = "bearerAuth")
public class PlacementDriveController {

    private final PlacementDriveService driveService;

    @PostMapping
    @Operation(summary = "Schedule a new placement drive")
    public ResponseEntity<PlacementDriveDto> createDrive(@Valid @RequestBody PlacementDriveDto driveDto) {
        return new ResponseEntity<>(driveService.createDrive(driveDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get drive details by ID")
    public ResponseEntity<PlacementDriveDto> getDriveById(@PathVariable Long id) {
        return ResponseEntity.ok(driveService.getDriveById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing placement drive details")
    public ResponseEntity<PlacementDriveDto> updateDrive(@PathVariable Long id, @Valid @RequestBody PlacementDriveDto driveDto) {
        return ResponseEntity.ok(driveService.updateDrive(id, driveDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancel or delete a placement drive by ID")
    public ResponseEntity<Void> deleteDrive(@PathVariable Long id) {
        driveService.deleteDrive(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get list of all drives with pagination, sorting, and optional search keyword")
    public ResponseEntity<Page<PlacementDriveDto>> getAllDrives(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(driveService.getAllDrives(search, pageable));
    }
}
