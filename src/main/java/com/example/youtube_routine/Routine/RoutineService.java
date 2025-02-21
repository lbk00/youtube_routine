package com.example.youtube_routine.Routine;

import java.util.List;

public interface RoutineService {
    RoutineResponseDTO createRoutine(String deviceId, RoutineRequestDTO requestDTO);
    List<RoutineResponseDTO> getUserRoutines(String deviceId);
    RoutineResponseDTO updateRoutine(Long routineId, RoutineRequestDTO requestDTO);
    void deleteRoutine(Long routineId);
}
