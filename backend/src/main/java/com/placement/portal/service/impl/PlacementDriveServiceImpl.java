package com.placement.portal.service.impl;

import com.placement.portal.dto.PlacementDriveDto;
import com.placement.portal.entity.PlacementDrive;
import com.placement.portal.exception.ResourceNotFoundException;
import com.placement.portal.mapper.DtoMapper;
import com.placement.portal.repository.PlacementDriveRepository;
import com.placement.portal.service.PlacementDriveService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlacementDriveServiceImpl implements PlacementDriveService {

    private final PlacementDriveRepository driveRepository;

    @Override
    @Transactional
    public PlacementDriveDto createDrive(PlacementDriveDto driveDto) {
        PlacementDrive drive = DtoMapper.toPlacementDriveEntity(driveDto);
        PlacementDrive savedDrive = driveRepository.save(drive);
        return DtoMapper.toPlacementDriveDto(savedDrive);
    }

    @Override
    public Page<PlacementDriveDto> getAllDrives(String search, Pageable pageable) {
        if (search != null && !search.trim().isEmpty()) {
            return driveRepository.findByCompanyNameContainingIgnoreCaseOrJobRoleContainingIgnoreCase(
                    search, search, pageable).map(DtoMapper::toPlacementDriveDto);
        }
        return driveRepository.findAll(pageable).map(DtoMapper::toPlacementDriveDto);
    }

    @Override
    public PlacementDriveDto getDriveById(Long id) {
        PlacementDrive drive = driveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Placement drive not found with ID: " + id));
        return DtoMapper.toPlacementDriveDto(drive);
    }

    @Override
    @Transactional
    public PlacementDriveDto updateDrive(Long id, PlacementDriveDto driveDto) {
        PlacementDrive drive = driveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Placement drive not found with ID: " + id));

        drive.setCompanyName(driveDto.getCompanyName());
        drive.setJobRole(driveDto.getJobRole());
        drive.setPackageOffered(driveDto.getPackageOffered());
        drive.setEligibilityCriteria(driveDto.getEligibilityCriteria());
        drive.setLocation(driveDto.getLocation());
        drive.setDriveDate(driveDto.getDriveDate());
        drive.setRegistrationDeadline(driveDto.getRegistrationDeadline());
        drive.setJobDescription(driveDto.getJobDescription());

        PlacementDrive updatedDrive = driveRepository.save(drive);
        return DtoMapper.toPlacementDriveDto(updatedDrive);
    }

    @Override
    @Transactional
    public void deleteDrive(Long id) {
        PlacementDrive drive = driveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Placement drive not found with ID: " + id));
        driveRepository.delete(drive);
    }
}
