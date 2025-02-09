package com.example.youtube_routine.Service;

import com.example.youtube_routine.Entity.Routine;
import com.example.youtube_routine.Entity.RoutineRequestDTO;
import com.example.youtube_routine.Entity.User;
import com.example.youtube_routine.Repository.RoutineRepository;
import com.example.youtube_routine.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService {

    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;

    @Override
    public Routine createRoutine(RoutineRequestDTO requestDTO) {
        User user = userRepository.findByDeviceId(requestDTO.getDeviceId()) // ⬅ deviceId로 조회
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Routine routine = Routine.builder()
                .day(requestDTO.getDay())
                .routineTime(requestDTO.getRoutineTime())
                .youtubeLink(requestDTO.getYoutubeLink())
                .content(requestDTO.getContent())
                .user(user)
                .build();

        return routineRepository.save(routine);
    }

    @Override
    public List<Routine> getUserRoutines(String deviceId) {
        User user = userRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        return routineRepository.findByUser(user);
    }

    @Override
    public Routine updateRoutine(Long routineId, RoutineRequestDTO requestDTO) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("Routine not found"));

        routine.setDay(requestDTO.getDay());
        routine.setRoutineTime(requestDTO.getRoutineTime());
        routine.setYoutubeLink(requestDTO.getYoutubeLink());
        routine.setContent(requestDTO.getContent());

        return routineRepository.save(routine);
    }

    @Override
    public void deleteRoutine(Long routineId) {
        routineRepository.deleteById(routineId);
    }
}