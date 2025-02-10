package com.example.youtube_routine.Service;

public interface NotificationService {
    void sendNotification(Long routineId);
    String getYoutubeLink(Long routineId);
}
