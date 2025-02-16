package com.example.youtube_routine.Routine;

import java.util.List;

public interface RoutineService {
    Routine createRoutine(String deviceId, RoutineRequestDTO requestDTO);
    List<Routine> getUserRoutines(String deviceId);
    Routine updateRoutine(Long routineId, RoutineRequestDTO requestDTO);
    void deleteRoutine(Long routineId);
}
