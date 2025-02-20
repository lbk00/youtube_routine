package com.example.youtube_routine.Notification;


import com.example.youtube_routine.Routine.Routine;
import com.example.youtube_routine.Routine.RoutineRepository;
import com.example.youtube_routine.User.User;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final RoutineRepository routineRepository;

    // 사용자가 저장한 루틴의 시간이 되면 푸시 알림을 보내는 메소드
    @Override
    public void sendNotification(Long routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("Routine not found"));

        User user = routine.getUser();
        String deviceId = user.getDeviceId();
        String message = "It's time for your routine: " + routine.getContent();

        // FCM 푸시 알림 전송 로직
        Message fcmMessage = Message.builder()
                .putData("title", "Routine Reminder")
                .putData("body", message)
                .putData("link", routine.getYoutubeLink())
                .setToken(deviceId)
                .build();

        FirebaseMessaging.getInstance().sendAsync(fcmMessage);
    }

    @Override
    public String getYoutubeLink(Long routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("Routine not found"));
        return routine.getYoutubeLink();
    }
}
