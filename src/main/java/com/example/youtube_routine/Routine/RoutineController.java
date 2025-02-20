package com.example.youtube_routine.Routine;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/routines")
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineService routineService;

    //  새로운 루틴 생성 api , 사용자로부터 요일 , 메시지 , 시간 , 링크 , 반복 여부 입력 -> 루틴 반환
    @PostMapping("/create/{deviceId}") // url로 deviceId 전달 , 다른 파라미터는 DTO로 전달
    public ResponseEntity<RoutineResponseDTO> createRoutine(@PathVariable String deviceId, @RequestBody RoutineRequestDTO requestDTO) {
        RoutineResponseDTO routineDTO = routineService.createRoutine(deviceId, requestDTO);
        return ResponseEntity.ok(routineDTO);
    }

    // 사용자 별 루틴 조회 api // deviceId로 조회
    @GetMapping("/user/{deviceId}")
    public ResponseEntity<List<Routine>> getUserRoutines(@PathVariable String deviceId) {
        List<Routine> routines = routineService.getUserRoutines(deviceId);
        return ResponseEntity.ok(routines);
    }

    // 기존 루틴 수정 api
    @PutMapping("/{routineId}")
    public ResponseEntity<Routine> updateRoutine(@PathVariable Long routineId, @RequestBody RoutineRequestDTO requestDTO) {
        Routine updatedRoutine = routineService.updateRoutine(routineId, requestDTO);
        return ResponseEntity.ok(updatedRoutine);
    }

    // 기존 루틴 삭제 api
    @DeleteMapping("/{routineId}")
    public ResponseEntity<Void> deleteRoutine(@PathVariable Long routineId) {
        routineService.deleteRoutine(routineId);
        return ResponseEntity.noContent().build();
    }
}
