package com.example.youtube_routine.Routine;

import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/routines")
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineService routineService;
    private final RoutineScheduler routineScheduler;

    //  새로운 루틴 생성 api , 사용자로부터 요일 , 메시지 , 시간 , 링크 , 반복 여부 입력 -> 루틴 반환
    @PostMapping("/create/{fcmToken}") // url로 deviceId 전달 , 다른 파라미터는 DTO로 전달
    public ResponseEntity<RoutineResponseDTO> createRoutine(@PathVariable String fcmToken, @RequestBody RoutineRequestDTO requestDTO) {
        RoutineResponseDTO routineDTO = routineService.createRoutine(fcmToken, requestDTO);
        return ResponseEntity.ok(routineDTO);
    }

    // 사용자 별 루틴 조회 api // deviceId로 조회
    @GetMapping("/user/{fcmToken}")
    public ResponseEntity<List<RoutineResponseDTO>> getUserRoutines(@PathVariable String fcmToken) {
        List<RoutineResponseDTO> routines = routineService.getUserRoutines(fcmToken);
        return ResponseEntity.ok(routines);
    }

    // 기존 루틴 수정 api
    @PutMapping("/{routineId}")
    public ResponseEntity<RoutineResponseDTO> updateRoutine(@PathVariable Long routineId, @RequestBody RoutineRequestDTO requestDTO) {
        RoutineResponseDTO updatedRoutine = routineService.updateRoutine(routineId, requestDTO);
        return ResponseEntity.ok(updatedRoutine);
    }

    // 기존 루틴 삭제 api
    @DeleteMapping("/{routineId}")
    public ResponseEntity<Void> deleteRoutine(@PathVariable Long routineId) {
        routineService.deleteRoutine(routineId);
        return ResponseEntity.noContent().build();
    }

    // 루틴 on/off 토글버튼 api
    @PutMapping("/toggle/{routineId}")
    public ResponseEntity<RoutineResponseDTO> toggleRoutineStatus(@PathVariable Long routineId) {
        RoutineResponseDTO updatedRoutine = routineService.toggleActive(routineId);
        return ResponseEntity.ok(updatedRoutine);
    }

    // 루틴 스케줄러 테스트용 api
    @GetMapping("/test/send-notifications")
    public ResponseEntity<String> testSendNotifications() throws FirebaseMessagingException {
        routineScheduler.checkRoutineNotifications();
        return ResponseEntity.ok("푸시 알림 테스트 실행 완료");
    }

}
