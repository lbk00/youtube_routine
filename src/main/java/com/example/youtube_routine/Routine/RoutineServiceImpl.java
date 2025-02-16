package com.example.youtube_routine.Routine;

import com.example.youtube_routine.User.User;
import com.example.youtube_routine.User.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService {

    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;


    // 루틴 생성
    @Override
    public Routine createRoutine(String deviceId, RoutineRequestDTO requestDTO) {
        User user = userRepository.findByDeviceId(deviceId) // ⬅ deviceId로 루틴을 생성한 사용자 조회
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Routine routine = Routine.builder()
                .day(requestDTO.getDay())
                .routineTime(requestDTO.getRoutineTime())
                .youtubeLink(requestDTO.getYoutubeLink())
                .content(requestDTO.getContent())
                .user(user)
                .repeat(requestDTO.isRepeat()) // boolean 타입은 get X -> is
                .build();

        return routineRepository.save(routine);
    }

    // 루틴 조회
    @Override
    public List<Routine> getUserRoutines(String deviceId) {
        return routineRepository.findByUserDeviceId(deviceId);
    }

    // 루틴 수정
    @Override
    public Routine updateRoutine(Long routineId, RoutineRequestDTO requestDTO) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("Routine not found"));

        routine.setDay(requestDTO.getDay());
        routine.setRoutineTime(requestDTO.getRoutineTime());
        routine.setYoutubeLink(requestDTO.getYoutubeLink());
        routine.setContent(requestDTO.getContent());
        routine.setRepeat(requestDTO.isRepeat());

        return routineRepository.save(routine);
    }

    @Override
    public void deleteRoutine(Long routineId) {
        routineRepository.deleteById(routineId);
    }
}