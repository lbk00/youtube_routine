package com.example.youtube_routine.Routine;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineScheduler {

    private final RoutineRepository routineRepository;

    // 매 분 0초마다 실행 (1분 간격)
    @Scheduled(cron = "0 * * * * *")
    public void checkRoutineNotifications() throws FirebaseMessagingException {
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")); // 현재 시간 "HH:mm"

        // 현재 시간과 일치하는 루틴 조회
        List<Routine> routines = routineRepository.findByRoutineTime(currentTime);

        for (Routine routine : routines) {
            if (routine.isActive()) { // 활성화된 루틴만 알림 전송
                sendPushNotification(
                        routine.getUser().getFcmToken(),
                        routine
                );
            }
        }
    }

    // FCM 푸시 알림을 직접 전송
    private void sendPushNotification(String fcmToken, Routine routine) throws FirebaseMessagingException {
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
            System.out.println("[FCM 전송 완료] 루틴 ID: " + routine.getId() + ", 응답: " + response);

        } catch (FirebaseMessagingException e) {
            System.err.println("[FCM 전송 실패] 루틴 ID: " + routine.getId());
            e.printStackTrace();
        }
    }
}
