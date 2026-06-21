package com.placement.portal.service.impl;

import com.placement.portal.dto.NotificationDto;
import com.placement.portal.entity.Notification;
import com.placement.portal.exception.ResourceNotFoundException;
import com.placement.portal.mapper.DtoMapper;
import com.placement.portal.repository.NotificationRepository;
import com.placement.portal.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public NotificationDto createNotification(NotificationDto dto) {
        Notification notification = DtoMapper.toNotificationEntity(dto);
        Notification savedNotification = notificationRepository.save(notification);
        return DtoMapper.toNotificationDto(savedNotification);
    }

    @Override
    public NotificationDto getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + id));
        return DtoMapper.toNotificationDto(notification);
    }

    @Override
    @Transactional
    public void deleteNotification(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + id));
        notificationRepository.delete(notification);
    }

    @Override
    public List<NotificationDto> getNotificationsByRecipient(String email) {
        return notificationRepository.findByRecipientEmailOrderByCreatedAtDesc(email).stream()
                .map(DtoMapper::toNotificationDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> getUnreadNotificationsByRecipient(String email) {
        return notificationRepository.findByRecipientEmailAndIsReadOrderByCreatedAtDesc(email, false).stream()
                .map(DtoMapper::toNotificationDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public NotificationDto markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + id));
        notification.setRead(true);
        Notification savedNotification = notificationRepository.save(notification);
        return DtoMapper.toNotificationDto(savedNotification);
    }
}
