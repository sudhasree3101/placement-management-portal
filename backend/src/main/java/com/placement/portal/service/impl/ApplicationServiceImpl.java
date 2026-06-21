package com.placement.portal.service.impl;

import com.placement.portal.dto.ApplicationDto;
import com.placement.portal.dto.ApplicationStatusRequest;
import com.placement.portal.entity.*;
import com.placement.portal.exception.DuplicateResourceException;
import com.placement.portal.exception.ResourceNotFoundException;
import com.placement.portal.mapper.DtoMapper;
import com.placement.portal.repository.ApplicationRepository;
import com.placement.portal.repository.PlacementDriveRepository;
import com.placement.portal.repository.StudentRepository;
import com.placement.portal.repository.NotificationRepository;
import com.placement.portal.repository.PlacementRecordRepository;
import com.placement.portal.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentRepository studentRepository;
    private final PlacementDriveRepository driveRepository;
    private final NotificationRepository notificationRepository;
    private final PlacementRecordRepository placementRecordRepository;

    @Override
    @Transactional
    public ApplicationDto applyForDrive(ApplicationDto dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + dto.getStudentId()));

        PlacementDrive drive = driveRepository.findById(dto.getPlacementDriveId())
                .orElseThrow(() -> new ResourceNotFoundException("Placement drive not found with ID: " + dto.getPlacementDriveId()));

        if (applicationRepository.existsByStudentIdAndPlacementDriveId(student.getId(), drive.getId())) {
            throw new DuplicateResourceException("Student has already applied for this placement drive");
        }

        if (drive.getRegistrationDeadline().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("The registration deadline for this drive has passed");
        }

        Application application = Application.builder()
                .student(student)
                .placementDrive(drive)
                .status(ApplicationStatus.APPLIED)
                .build();

        Application savedApp = applicationRepository.save(application);

        Notification notification = Notification.builder()
                .recipientEmail(student.getEmail())
                .message("You have successfully applied for the " + drive.getJobRole() + " role at " + drive.getCompanyName() + ".")
                .isRead(false)
                .build();
        notificationRepository.save(notification);

        return DtoMapper.toApplicationDto(savedApp);
    }

    @Override
    public List<ApplicationDto> getApplicationsByStudentId(Long studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found with ID: " + studentId);
        }
        return applicationRepository.findByStudentId(studentId).stream()
                .map(DtoMapper::toApplicationDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationDto> getApplicationsByDriveId(Long driveId) {
        if (!driveRepository.existsById(driveId)) {
            throw new ResourceNotFoundException("Placement drive not found with ID: " + driveId);
        }
        return applicationRepository.findByPlacementDriveId(driveId).stream()
                .map(DtoMapper::toApplicationDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ApplicationDto updateApplicationStatus(ApplicationStatusRequest request) {
        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + request.getApplicationId()));

        ApplicationStatus status;
        try {
            status = ApplicationStatus.valueOf(request.getStatus().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value: " + request.getStatus());
        }

        application.setStatus(status);
        Application updatedApp = applicationRepository.save(application);

        Student student = application.getStudent();
        PlacementDrive drive = application.getPlacementDrive();

        Notification notification = Notification.builder()
                .recipientEmail(student.getEmail())
                .message("Your application status for " + drive.getJobRole() + " at " + drive.getCompanyName() + " has been updated to " + status + ".")
                .isRead(false)
                .build();
        notificationRepository.save(notification);

        if (status == ApplicationStatus.SELECTED) {
            student.setPlacementStatus(PlacementStatus.PLACED);
            studentRepository.save(student);

            boolean recordExists = placementRecordRepository.findByStudentId(student.getId()).stream()
                    .anyMatch(r -> r.getCompanyName().equalsIgnoreCase(drive.getCompanyName()) && r.getJobRole().equalsIgnoreCase(drive.getJobRole()));
            
            if (!recordExists) {
                PlacementRecord record = PlacementRecord.builder()
                        .student(student)
                        .companyName(drive.getCompanyName())
                        .jobRole(drive.getJobRole())
                        .packageAmount(drive.getPackageOffered())
                        .joiningDate(LocalDate.now().plusMonths(3))
                        .build();
                placementRecordRepository.save(record);
            }
        } else if (status == ApplicationStatus.REJECTED) {
            boolean isPlaced = applicationRepository.findByStudentId(student.getId()).stream()
                    .anyMatch(a -> a.getStatus() == ApplicationStatus.SELECTED);
            if (!isPlaced) {
                student.setPlacementStatus(PlacementStatus.UNPLACED);
                studentRepository.save(student);
            }
        }

        return DtoMapper.toApplicationDto(updatedApp);
    }

    @Override
    public List<ApplicationDto> getAllApplications() {
        return applicationRepository.findAll().stream()
                .map(DtoMapper::toApplicationDto)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationDto getApplicationById(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + id));
        return DtoMapper.toApplicationDto(application);
    }
}
