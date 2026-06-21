package com.placement.portal.service;

import com.placement.portal.dto.NotificationDto;

import java.util.List;

public interface NotificationService {
    NotificationDto createNotification(NotificationDto notificationDto);
    NotificationDto getNotificationById(Long id);
    void deleteNotification(Long id);
    List<NotificationDto> getNotificationsByRecipient(String email);
    List<NotificationDto> getUnreadNotificationsByRecipient(String email);
    NotificationDto markAsRead(Long id);
}
