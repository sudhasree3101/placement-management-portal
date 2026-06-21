package com.placement.portal.controller;

import com.placement.portal.dto.NotificationDto;
import com.placement.portal.service.NotificationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Tag(name = "Notification Module", description = "APIs to send, retrieve, and mark notifications")
@SecurityRequirement(name = "bearerAuth")
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    @Operation(summary = "Publish a new notification manually")
    public ResponseEntity<NotificationDto> createNotification(@Valid @RequestBody NotificationDto notificationDto) {
        return new ResponseEntity<>(notificationService.createNotification(notificationDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get notification details by ID")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a notification entry by ID")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @Operation(summary = "Get list of notifications for the authenticated user")
    public ResponseEntity<List<NotificationDto>> getNotificationsForCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(notificationService.getNotificationsByRecipient(userDetails.getUsername()));
    }

    @GetMapping("/unread")
    @Operation(summary = "Get unread notifications list for the authenticated user")
    public ResponseEntity<List<NotificationDto>> getUnreadNotificationsForCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(notificationService.getUnreadNotificationsByRecipient(userDetails.getUsername()));
    }

    @PutMapping("/{id}/read")
    @Operation(summary = "Mark a notification as read by ID")
    public ResponseEntity<NotificationDto> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }
}
