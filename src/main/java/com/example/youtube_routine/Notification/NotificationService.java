package com.example.youtube_routine.Notification;

import com.example.youtube_routine.Routine.Routine;

public interface NotificationService {
    //fcm토큰으로 사용자의 루틴 목록 조회
    void sendPushNotificationByFcmToken(String fcmToken);
}
