package com.example.youtube_routine.Routine;

import com.example.youtube_routine.User.User;
import com.example.youtube_routine.User.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoutineServiceImpl implements RoutineService {

    private final RoutineRepository routineRepository;
    private final UserRepository userRepository;

    // 직렬화에서 무한 중첩을 벗어나기위해 엔티티 -> DTO 변환 메서드
    public RoutineResponseDTO toRoutineResponseDTO(Routine routine) {
        return new RoutineResponseDTO(routine);
    }


    // 루틴 생성
    @Override
    @Transactional
    public RoutineResponseDTO createRoutine(String deviceId, RoutineRequestDTO requestDTO) {
        User user = userRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with deviceId: " + deviceId));

        // 사용자별 루틴 최대 10개로 제한
        if (routineRepository.countByUser(user) >= 10) {
            throw new IllegalStateException("Each user can have a maximum of 10 routines.");
        }

        Routine routine = Routine.builder()
                .day(requestDTO.getDay())
                .routineTime(requestDTO.getRoutineTime())
                .youtubeLink(requestDTO.getYoutubeLink())
                .content(requestDTO.getContent())
                .repeatFlag(requestDTO.isRepeatFlag())
                .user(user)
                .build();

        routine = routineRepository.save(routine);
        return toRoutineResponseDTO(routine);
    }

    // 루틴 조회
    @Override
    @Transactional
    public List<RoutineResponseDTO> getUserRoutines(String deviceId) {
        User user = userRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("User with deviceId '" + deviceId + "' not found"));

        List<Routine> routines = routineRepository.findByUserDeviceId(deviceId);
        return routines.stream()
                .map(RoutineResponseDTO::new)
                .collect(Collectors.toList());
    }

    // 루틴 수정
    @Override
    @Transactional
    public RoutineResponseDTO updateRoutine(Long routineId, RoutineRequestDTO requestDTO) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("Routine not found with ID: " + routineId));

        routine.setDay(requestDTO.getDay());
        routine.setRoutineTime(requestDTO.getRoutineTime());
        routine.setYoutubeLink(requestDTO.getYoutubeLink());
        routine.setContent(requestDTO.getContent());
        routine.setRepeatFlag(requestDTO.isRepeatFlag());

        return toRoutineResponseDTO(routine);
    }

    @Override
    @Transactional
    public void deleteRoutine(Long routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("Routine not found with ID: " + routineId));

        routineRepository.delete(routine);
    }
}