package com.example.youtube_routine.Notification;

public interface NotificationService {
    void sendNotification(Long routineId);
    String getYoutubeLink(Long routineId);
}
