package com.example.youtube_routine.Routine.Service;

import com.example.youtube_routine.Routine.DTO.RoutineRequestDTO;
import com.example.youtube_routine.Routine.DTO.RoutineResponseDTO;
import com.example.youtube_routine.Routine.Entity.Routine;
import com.example.youtube_routine.Routine.Repository.RoutineRepository;
import com.example.youtube_routine.User.User;
import com.example.youtube_routine.User.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    public RoutineResponseDTO createRoutine(String fcmToken, RoutineRequestDTO requestDTO) {
        User user = userRepository.findByFcmToken(fcmToken)
                .orElseThrow(() -> new EntityNotFoundException("User not found with fcmToken: " + fcmToken));

        if (routineRepository.countByUser(user) >= 10) {
            throw new IllegalStateException("Each user can have a maximum of 10 routines.");
        }

        Routine routine = Routine.builder()
                .routineTime(requestDTO.getRoutineTime())
                .youtubeLink(requestDTO.getYoutubeLink())
                .content(requestDTO.getContent())
                .repeatFlag(requestDTO.isRepeatFlag())
                .user(user)
                .build();

        routine.setDays(Optional.ofNullable(requestDTO.getDays()).orElse(List.of())); // NULL 방지

        //활동 시간 갱신
        user.setLastActiveAt(LocalDateTime.now());
        user.setActive(true);
        userRepository.save(user);
        routine = routineRepository.save(routine);
        return toRoutineResponseDTO(routine);
    }



    // 루틴 조회
    @Override
    @Transactional
    public List<RoutineResponseDTO> getUserRoutines(String fcmToken) {
        User user = userRepository.findByFcmToken(fcmToken)
                .orElseThrow(() -> new EntityNotFoundException("User with fcmToken '" + fcmToken + "' not found"));

        List<Routine> routines = routineRepository.findByUser(user);
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

        // days 값이 null이면 빈 리스트로 설정 (빈칸 방지)
        routine.setDays(Optional.ofNullable(requestDTO.getDays()).orElse(List.of()));

        routine.setRoutineTime(requestDTO.getRoutineTime());
        routine.setYoutubeLink(requestDTO.getYoutubeLink());
        routine.setContent(requestDTO.getContent());
        routine.setRepeatFlag(requestDTO.isRepeatFlag());
        routine.setActive(true); // 수정 시 isActive true로 변경

        //루틴 소유자의 활동 시간 갱신
        User user = routine.getUser();
        user.setLastActiveAt(LocalDateTime.now());
        user.setActive(true);
        userRepository.save(user);

        return toRoutineResponseDTO(routine);
    }

    @Override
    @Transactional
    public void deleteRoutine(Long routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("Routine not found with ID: " + routineId));

        User user = routine.getUser();
        user.setLastActiveAt(LocalDateTime.now()); //  활동 시간 갱신
        user.setActive(true);
        userRepository.save(user);
        routineRepository.deleteById(routineId);
    }

    @Override
    @Transactional
    public RoutineResponseDTO toggleActive(Long routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("Routine not found with ID: " + routineId));

        // 현재 상태 반전 (true → false, false → true)
        routine.setActive(!routine.isActive());

        User user = routine.getUser();
        user.setLastActiveAt(LocalDateTime.now());
        user.setActive(true);
        userRepository.save(user); //  활동 시간 갱신

        return new RoutineResponseDTO(routine);
    }
}