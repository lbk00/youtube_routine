package com.example.youtube_routine.Controller;

import com.example.youtube_routine.Entity.Routine;
import com.example.youtube_routine.Entity.RoutineRequestDTO;
import com.example.youtube_routine.Service.RoutineService;
import com.example.youtube_routine.Service.RoutineServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/routines")
@RequiredArgsConstructor
public class RoutineController {

    private final RoutineService routineService;

    //  새로운 루틴 생성 api , 사용자로부터 요일 , 메시지 , 시간 , 링크 입력 -> 루틴 반환
    @PostMapping("/create")
    public ResponseEntity<Routine> createRoutine(@RequestBody RoutineRequestDTO requestDTO) {
        Routine routine = routineService.createRoutine(requestDTO);
        return ResponseEntity.ok(routine);
    }

    // 사용자 별 루틴 조회 api // id 또는 deviceId로 조회
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
