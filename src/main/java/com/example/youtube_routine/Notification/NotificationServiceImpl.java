package com.example.youtube_routine.Notification;


import com.example.youtube_routine.Routine.Routine;
import com.example.youtube_routine.Routine.RoutineRepository;
import com.example.youtube_routine.User.User;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final RoutineRepository routineRepository;


    public void sendPushNotificationByFcmToken(String fcmToken) {
        //해당 사용자의 루틴 목록 조회
        List<Routine> routines = routineRepository.findByUserFcmToken(fcmToken);

        if (routines.isEmpty()) {
            throw new EntityNotFoundException("No routines found for this user");
        }

        for (Routine routine : routines) {
            if (routine.isActive()) { //활성화된 루틴만 푸시 알림 전송
                sendPushNotification(fcmToken, routine);
            }
        }
    }

    // 푸시 알림 보내는 메소드
    private void sendPushNotification(String fcmToken, Routine routine) {
        try {
            Message fcmMessage = Message.builder()
                    .setToken(fcmToken)
                    .setNotification(Notification.builder()
                            .setTitle("오늘의 루틴") // 알림 제목
                            .setBody(routine.getContent()) // 알림 내용
                            .build())
                    .putData("youtubeLink", routine.getYoutubeLink()) // 유튜브 링크
                    .build(); // 알림 클릭하면 바로 해당 유튜브 영상으로 이동

            String response = FirebaseMessaging.getInstance().send(fcmMessage);
            System.out.println("FCM 푸시 알림 전송 완료: " + response);

        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }

}
