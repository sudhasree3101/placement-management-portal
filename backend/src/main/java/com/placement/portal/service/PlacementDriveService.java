package com.placement.portal.service;

import com.placement.portal.dto.PlacementDriveDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PlacementDriveService {
    PlacementDriveDto createDrive(PlacementDriveDto driveDto);
    PlacementDriveDto getDriveById(Long id);
    PlacementDriveDto updateDrive(Long id, PlacementDriveDto driveDto);
    void deleteDrive(Long id);
    Page<PlacementDriveDto> getAllDrives(String search, Pageable pageable);
}
