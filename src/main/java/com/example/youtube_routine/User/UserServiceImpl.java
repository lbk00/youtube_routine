package com.example.youtube_routine.User;

import com.example.youtube_routine.Exception.GlobalExceptionHandler;
import com.example.youtube_routine.Routine.RoutineRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoutineRepository routineRepository;

    public UserResponseDTO toUserResponseDTO(User user) {
        return new UserResponseDTO(user);
    }

    @Override
    @Transactional
    public UserResponseDTO registerUser(String fcmToken) {
        if (userRepository.findByFcmToken(fcmToken).isPresent()) {
            throw new IllegalStateException("이미 등록된 사용자입니다." );
        }
        User user = User.builder()
                .fcmToken(fcmToken)
                .routines(List.of())
                .build();

        user.setLastActiveAt(LocalDateTime.now());   // 초기 활성화시간
        user.setActive(true);

        user = userRepository.save(user);
        return toUserResponseDTO(user);
    }

    @Override
    @Transactional
    public UserResponseDTO getUser(String fcmToken) {
        User user = userRepository.findByFcmToken(fcmToken)
                .orElseThrow(() -> new EntityNotFoundException("User not found with fcmToken: " + fcmToken));
        return toUserResponseDTO(user);
    }

    // 사용자 fcm 토큰 업데이트
    @Override
    @Transactional
    public UserResponseDTO updateUser(String fcmToken, String newFcmToken) {
        User user = userRepository.findByFcmToken(fcmToken)
                .orElseThrow(() -> new EntityNotFoundException("User not found with fcmToken: " + fcmToken));

        user.setFcmToken(newFcmToken);
        userRepository.save(user);
        return toUserResponseDTO(user);
    }

    // 사용자 삭제
    @Override
    @Transactional
    public void deleteUser(String fcmToken) {
        User user = userRepository.findByFcmToken(fcmToken)
                .orElseThrow(() -> new EntityNotFoundException("User not found with fcmToken: " + fcmToken));
        routineRepository.deleteAllByUser(user);
        userRepository.delete(user);
    }

}
