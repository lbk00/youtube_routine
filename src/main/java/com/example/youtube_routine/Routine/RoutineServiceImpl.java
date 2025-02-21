package com.example.youtube_routine.Routine;

import com.example.youtube_routine.User.User;
import com.example.youtube_routine.User.UserRepository;
import jakarta.persistence.EntityNotFoundException;
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
        return new RoutineResponseDTO(
                routine.getDay(),
                routine.getRoutineTime(),
                routine.getYoutubeLink(),
                routine.getContent(),
                routine.isRepeatFlag()
        );
    }


    // 루틴 생성
    @Override
    public RoutineResponseDTO createRoutine(String deviceId, RoutineRequestDTO requestDTO) {
        User user = userRepository.findByDeviceId(deviceId) // ⬅ deviceId로 루틴을 생성한 사용자 조회
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // 사용자별 루틴 최대 10개로 제한
        if (routineRepository.countByUser(user) >= 10) {
            throw new IllegalStateException("Each user can have a maximum of 10 routines.");
        }

        Routine routine = Routine.builder()
                .day(requestDTO.getDay())
                .routineTime(requestDTO.getRoutineTime())
                .youtubeLink(requestDTO.getYoutubeLink())
                .content(requestDTO.getContent())
                .user(user)
                .repeatFlag(requestDTO.isRepeatFlag()) // boolean 타입은 get X -> is
                .build();

        routineRepository.save(routine);
        return toRoutineResponseDTO(routine);
    }

    // 루틴 조회
    @Override
    public List<RoutineResponseDTO> getUserRoutines(String deviceId) {
        List<Routine> routines = routineRepository.findByUserDeviceId(deviceId);
        return routines.stream()
                .map(routine -> new RoutineResponseDTO(
                        routine.getDay(),
                        routine.getRoutineTime(),
                        routine.getYoutubeLink(),
                        routine.getContent(),
                        routine.isRepeatFlag()
                ))
                .collect(Collectors.toList());
    }

    // 루틴 수정
    @Override
    public RoutineResponseDTO updateRoutine(Long routineId, RoutineRequestDTO requestDTO) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("Routine not found"));

        routine.setDay(requestDTO.getDay());
        routine.setRoutineTime(requestDTO.getRoutineTime());
        routine.setYoutubeLink(requestDTO.getYoutubeLink());
        routine.setContent(requestDTO.getContent());
        routine.setRepeatFlag(requestDTO.isRepeatFlag());

        routineRepository.save(routine);

        return toRoutineResponseDTO(routine);
    }

    @Override
    public void deleteRoutine(Long routineId) {
        if (!routineRepository.existsById(routineId)) {
            throw new EntityNotFoundException("Routine not found");
        }
        routineRepository.deleteById(routineId);
    }
}