package com.placement.portal.service;

import com.placement.portal.dto.ApplicationDto;
import com.placement.portal.dto.ApplicationStatusRequest;

import java.util.List;

public interface ApplicationService {
    ApplicationDto applyForDrive(ApplicationDto applicationDto);
    List<ApplicationDto> getApplicationsByStudentId(Long studentId);
    List<ApplicationDto> getApplicationsByDriveId(Long driveId);
    ApplicationDto updateApplicationStatus(ApplicationStatusRequest request);
    List<ApplicationDto> getAllApplications();
    ApplicationDto getApplicationById(Long id);
}
