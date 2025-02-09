package com.example.youtube_routine.Service;

import com.example.youtube_routine.Entity.Routine;
import com.example.youtube_routine.Entity.RoutineRequestDTO;

import java.util.List;

public interface RoutineService {
    Routine createRoutine(RoutineRequestDTO requestDTO);
    List<Routine> getUserRoutines(String deviceId);
    Routine updateRoutine(Long routineId, RoutineRequestDTO requestDTO);
    void deleteRoutine(Long routineId);
}
