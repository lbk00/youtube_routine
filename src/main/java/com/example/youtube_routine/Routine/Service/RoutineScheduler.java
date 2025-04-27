package com.example.youtube_routine.Routine.Service;

import com.example.youtube_routine.Routine.Entity.Routine;
import com.example.youtube_routine.Routine.Repository.RoutineRepository;
import com.example.youtube_routine.User.Entity.User;
import com.example.youtube_routine.User.Repository.UserRepository;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineScheduler {

    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;

    // 매 분 0초마다 실행 (1분 간격)
    @Scheduled(cron = "0 * * * * *")
    public void checkRoutineNotifications() {
        String currentDay = LocalDate.now().getDayOfWeek().name(); // 현재 요일 (예: MONDAY)
        String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")); // 현재 시간 (HH:mm)

        // 현재 시간과 일치하는 루틴 조회
        List<Routine> routines = routineRepository.findByRoutineTimeWithUser(currentTime);

//        System.out.println("[디버깅] 현재 요일: " + currentDay + ", 현재 시간: " + currentTime);
//        System.out.println("[디버깅] 검색된 루틴 개수: " + routines.size());

        for (Routine routine : routines) {
////            System.out.println("[디버깅] 루틴 ID: " + routine.getId());

            if (!routine.isActive()) {
////                System.out.println("루틴 비활성화됨");
                continue;
            }

            if (routine.getUser() == null) {
////                System.out.println("유저 정보 없음");
                continue;
            }

            String fcmToken = routine.getUser().getFcmToken();
            if (fcmToken == null || fcmToken.isEmpty()) {
////                System.out.println("FCM 토큰 없음");
                continue;
            }

            if (!isRoutineDayMatched(routine.getDays(), currentDay)) {
////                System.out.println("오늘 요일(" + currentDay + ")이 루틴 요일에 포함되지 않음: " + routine.getDays());
                continue;
            }

            // 모든 조건 통과
//            System.out.println("FCM 전송 시작");

            // FCM 전송 시도 및 실패 감지시 사용자 isActive = false
            // UserScheduler가 30일 이후 자동 삭제
            try {
                sendPushNotification(fcmToken, routine);
            } catch (FirebaseMessagingException e) {
////                System.err.println("[FCM 전송 실패] 루틴 ID: " + routine.getId() + ", 이유: " + e.getMessage());

                if (e.getMessagingErrorCode() == MessagingErrorCode.UNREGISTERED) {
                    User user = routine.getUser();

                    user.setActive(false); // 비활성화 마킹
                    userRepository.save(user);
////                    System.out.println("FCM 토큰 무효 → 사용자 isActive=false 처리 완료");
                }

                continue;
            }

            // 반복 플래그 비활성화 : 1번 실행 후 is_active = false로 변경
            if (!routine.isRepeatFlag()) {
                routine.setActive(false);
                routineRepository.save(routine);
////                System.out.println("반복 없음 → 루틴 비활성화 처리 완료");
            }
        }
    }

    // 요일이 일치하는지 확인하는 메서드
    private boolean isRoutineDayMatched(String days, String currentDay) {
        if (days == null || days.isEmpty()) return false;

        List<String> dayList = Arrays.stream(days.split(","))
                .map(String::trim)
                .map(String::toUpperCase)
                .toList();

        return dayList.contains(currentDay.toUpperCase());
    }


    // FCM 푸시 알림 전송
    private void sendPushNotification(String fcmToken, Routine routine) throws FirebaseMessagingException {
        try {
//            System.out.println("[FCM 시도] 루틴 ID: " + routine.getId());
//            System.out.println("FCM 토큰: " + fcmToken);
//            System.out.println("유튜브 링크: " + routine.getYoutubeLink());

            Message fcmMessage = Message.builder()
                    .setToken(fcmToken)
                    .putData("title", routine.getContent()) // 알림 제목
                    .putData("body", "오늘 할 루틴이 도착했어요!")      // 알림 내용
                    .putData("youtubeLink", routine.getYoutubeLink()) // 유튜브 링크
                    .build();


            String response = FirebaseMessaging.getInstance().send(fcmMessage);
//            System.out.println("[FCM 전송 완료] 응답: " + response);

        } catch (FirebaseMessagingException e) {
//            System.err.println("[FCM 전송 실패] 루틴 ID: " + routine.getId() + " 이유: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
