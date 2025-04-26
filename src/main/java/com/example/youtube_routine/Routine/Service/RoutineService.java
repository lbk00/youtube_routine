package com.example.youtube_routine.Routine.Service;

import com.example.youtube_routine.Routine.DTO.RoutineRequestDTO;
import com.example.youtube_routine.Routine.DTO.RoutineResponseDTO;

import java.util.List;

public interface RoutineService {
    // 루틴 생성
    RoutineResponseDTO createRoutine(String deviceId, RoutineRequestDTO requestDTO);
    // 루틴 조회
    List<RoutineResponseDTO> getUserRoutines(String deviceId);
    // 루틴 수정
    RoutineResponseDTO updateRoutine(Long routineId, RoutineRequestDTO requestDTO);
    // 루틴 삭제
    void deleteRoutine(Long routineId);
    // 루틴 활성화/비활성화
    RoutineResponseDTO toggleActive(Long routineId);
}
