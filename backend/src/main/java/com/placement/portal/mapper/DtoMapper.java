package com.placement.portal.mapper;

import com.placement.portal.dto.*;
import com.placement.portal.entity.*;

public class DtoMapper {

    public static UserDto toUserDto(User user) {
        if (user == null) return null;
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .enabled(user.isEnabled())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static StudentDto toStudentDto(Student student) {
        if (student == null) return null;
        return StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .phone(student.getPhone())
                .department(student.getDepartment())
                .cgpa(student.getCgpa())
                .skills(student.getSkills())
                .graduationYear(student.getGraduationYear())
                .resumeUrl(student.getResumeUrl())
                .placementStatus(student.getPlacementStatus() != null ? student.getPlacementStatus().name() : null)
                .build();
    }

    public static Student toStudentEntity(StudentDto dto) {
        if (dto == null) return null;
        return Student.builder()
                .id(dto.getId())
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .department(dto.getDepartment())
                .cgpa(dto.getCgpa())
                .skills(dto.getSkills())
                .graduationYear(dto.getGraduationYear())
                .resumeUrl(dto.getResumeUrl())
                .placementStatus(dto.getPlacementStatus() != null ? PlacementStatus.valueOf(dto.getPlacementStatus()) : PlacementStatus.UNPLACED)
                .build();
    }

    public static RecruiterDto toRecruiterDto(Recruiter recruiter) {
        if (recruiter == null) return null;
        return RecruiterDto.builder()
                .id(recruiter.getId())
                .companyName(recruiter.getCompanyName())
                .hrName(recruiter.getHrName())
                .email(recruiter.getEmail())
                .phone(recruiter.getPhone())
                .website(recruiter.getWebsite())
                .industry(recruiter.getIndustry())
                .build();
    }

    public static Recruiter toRecruiterEntity(RecruiterDto dto) {
        if (dto == null) return null;
        return Recruiter.builder()
                .id(dto.getId())
                .companyName(dto.getCompanyName())
                .hrName(dto.getHrName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .website(dto.getWebsite())
                .industry(dto.getIndustry())
                .build();
    }

    public static PlacementDriveDto toPlacementDriveDto(PlacementDrive drive) {
        if (drive == null) return null;
        return PlacementDriveDto.builder()
                .id(drive.getId())
                .companyName(drive.getCompanyName())
                .jobRole(drive.getJobRole())
                .packageOffered(drive.getPackageOffered())
                .eligibilityCriteria(drive.getEligibilityCriteria())
                .location(drive.getLocation())
                .driveDate(drive.getDriveDate())
                .registrationDeadline(drive.getRegistrationDeadline())
                .jobDescription(drive.getJobDescription())
                .build();
    }

    public static PlacementDrive toPlacementDriveEntity(PlacementDriveDto dto) {
        if (dto == null) return null;
        return PlacementDrive.builder()
                .id(dto.getId())
                .companyName(dto.getCompanyName())
                .jobRole(dto.getJobRole())
                .packageOffered(dto.getPackageOffered())
                .eligibilityCriteria(dto.getEligibilityCriteria())
                .location(dto.getLocation())
                .driveDate(dto.getDriveDate())
                .registrationDeadline(dto.getRegistrationDeadline())
                .jobDescription(dto.getJobDescription())
                .build();
    }

    public static ApplicationDto toApplicationDto(Application app) {
        if (app == null) return null;
        return ApplicationDto.builder()
                .id(app.getId())
                .studentId(app.getStudent() != null ? app.getStudent().getId() : null)
                .placementDriveId(app.getPlacementDrive() != null ? app.getPlacementDrive().getId() : null)
                .studentName(app.getStudent() != null ? app.getStudent().getName() : null)
                .studentEmail(app.getStudent() != null ? app.getStudent().getEmail() : null)
                .companyName(app.getPlacementDrive() != null ? app.getPlacementDrive().getCompanyName() : null)
                .jobRole(app.getPlacementDrive() != null ? app.getPlacementDrive().getJobRole() : null)
                .status(app.getStatus() != null ? app.getStatus().name() : null)
                .appliedDate(app.getAppliedDate())
                .build();
    }

    public static InterviewRoundDto toInterviewRoundDto(InterviewRound round) {
        if (round == null) return null;
        return InterviewRoundDto.builder()
                .id(round.getId())
                .applicationId(round.getApplication() != null ? round.getApplication().getId() : null)
                .roundName(round.getRoundName())
                .status(round.getStatus() != null ? round.getStatus().name() : null)
                .feedback(round.getFeedback())
                .scheduledDate(round.getScheduledDate())
                .build();
    }

    public static NotificationDto toNotificationDto(Notification notification) {
        if (notification == null) return null;
        return NotificationDto.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .recipientEmail(notification.getRecipientEmail())
                .isRead(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }

    public static Notification toNotificationEntity(NotificationDto dto) {
        if (dto == null) return null;
        return Notification.builder()
                .id(dto.getId())
                .message(dto.getMessage())
                .recipientEmail(dto.getRecipientEmail())
                .isRead(dto.isRead())
                .build();
    }

    public static PlacementRecordDto toPlacementRecordDto(PlacementRecord record) {
        if (record == null) return null;
        return PlacementRecordDto.builder()
                .id(record.getId())
                .studentId(record.getStudent() != null ? record.getStudent().getId() : null)
                .studentName(record.getStudent() != null ? record.getStudent().getName() : null)
                .studentEmail(record.getStudent() != null ? record.getStudent().getEmail() : null)
                .companyName(record.getCompanyName())
                .jobRole(record.getJobRole())
                .packageAmount(record.getPackageAmount())
                .joiningDate(record.getJoiningDate())
                .build();
    }
}
