package com.example.youtube_routine.Controller;

import com.example.youtube_routine.Service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    // 설정한 루틴의 시간이 되었을 때 알림 푸시
    @GetMapping("/send/{routineId}")
    public ResponseEntity<String> sendNotification(@PathVariable Long routineId) {
        notificationService.sendNotification(routineId);
        return ResponseEntity.ok("푸시 알림 전송 완료");
    }

    // 알림 클릭 시 유튜브 링크 반환
    @GetMapping("/open/{routineId}")
    public ResponseEntity<String> openRoutineLink(@PathVariable Long routineId) {
        String youtubeLink = notificationService.getYoutubeLink(routineId);
        return ResponseEntity.ok(youtubeLink);
    }
}
