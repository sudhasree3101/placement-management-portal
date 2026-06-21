package com.placement.portal.service.impl;

import com.placement.portal.dto.InterviewRoundDto;
import com.placement.portal.entity.*;
import com.placement.portal.exception.ResourceNotFoundException;
import com.placement.portal.mapper.DtoMapper;
import com.placement.portal.repository.ApplicationRepository;
import com.placement.portal.repository.InterviewRoundRepository;
import com.placement.portal.repository.NotificationRepository;
import com.placement.portal.service.InterviewRoundService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterviewRoundServiceImpl implements InterviewRoundService {

    private final InterviewRoundRepository roundRepository;
    private final ApplicationRepository applicationRepository;
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public InterviewRoundDto createRound(InterviewRoundDto dto) {
        Application application = applicationRepository.findById(dto.getApplicationId())
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with ID: " + dto.getApplicationId()));

        InterviewRound round = InterviewRound.builder()
                .application(application)
                .roundName(dto.getRoundName())
                .status(dto.getStatus() != null ? InterviewStatus.valueOf(dto.getStatus().toUpperCase()) : InterviewStatus.PENDING)
                .feedback(dto.getFeedback())
                .scheduledDate(dto.getScheduledDate())
                .build();

        InterviewRound savedRound = roundRepository.save(round);

        Student student = application.getStudent();
        PlacementDrive drive = application.getPlacementDrive();
        
        Notification notification = Notification.builder()
                .recipientEmail(student.getEmail())
                .message("A new interview round '" + round.getRoundName() + "' has been scheduled for " + 
                        drive.getJobRole() + " at " + drive.getCompanyName() + " on " + round.getScheduledDate() + ".")
                .isRead(false)
                .build();
        notificationRepository.save(notification);

        return DtoMapper.toInterviewRoundDto(savedRound);
    }

    @Override
    public InterviewRoundDto getRoundById(Long id) {
        InterviewRound round = roundRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview round not found with ID: " + id));
        return DtoMapper.toInterviewRoundDto(round);
    }

    @Override
    @Transactional
    public InterviewRoundDto updateRound(Long id, InterviewRoundDto dto) {
        InterviewRound round = roundRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview round not found with ID: " + id));

        InterviewStatus newStatus = dto.getStatus() != null ? InterviewStatus.valueOf(dto.getStatus().toUpperCase()) : round.getStatus();
        round.setRoundName(dto.getRoundName());
        round.setStatus(newStatus);
        round.setFeedback(dto.getFeedback());
        round.setScheduledDate(dto.getScheduledDate());

        InterviewRound updatedRound = roundRepository.save(round);

        Application application = round.getApplication();
        Student student = application.getStudent();
        PlacementDrive drive = application.getPlacementDrive();

        if (newStatus == InterviewStatus.PASSED) {
            Notification notification = Notification.builder()
                    .recipientEmail(student.getEmail())
                    .message("Congratulations! You passed the interview round '" + round.getRoundName() + 
                            "' for " + drive.getJobRole() + " at " + drive.getCompanyName() + ".")
                    .isRead(false)
                    .build();
            notificationRepository.save(notification);

            if (application.getStatus() == ApplicationStatus.APPLIED) {
                application.setStatus(ApplicationStatus.SHORTLISTED);
                applicationRepository.save(application);
            }
        } else if (newStatus == InterviewStatus.FAILED) {
            Notification notification = Notification.builder()
                    .recipientEmail(student.getEmail())
                    .message("We regret to inform you that you did not clear the interview round '" + round.getRoundName() + 
                            "' for " + drive.getJobRole() + " at " + drive.getCompanyName() + ".")
                    .isRead(false)
                    .build();
            notificationRepository.save(notification);

            application.setStatus(ApplicationStatus.REJECTED);
            applicationRepository.save(application);
        }

        return DtoMapper.toInterviewRoundDto(updatedRound);
    }

    @Override
    @Transactional
    public void deleteRound(Long id) {
        InterviewRound round = roundRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interview round not found with ID: " + id));
        roundRepository.delete(round);
    }

    @Override
    public List<InterviewRoundDto> getRoundsByApplicationId(Long applicationId) {
        if (!applicationRepository.existsById(applicationId)) {
            throw new ResourceNotFoundException("Application not found with ID: " + applicationId);
        }
        return roundRepository.findByApplicationId(applicationId).stream()
                .map(DtoMapper::toInterviewRoundDto)
                .collect(Collectors.toList());
    }
}
