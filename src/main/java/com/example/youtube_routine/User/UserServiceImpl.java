package com.example.youtube_routine.User;

import com.example.youtube_routine.Exception.GlobalExceptionHandler;
import com.example.youtube_routine.Routine.RoutineRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    // 사용자가 어플을 최초 실행시 기기 고유번호릉 가져옴
    // 기기번호는 클라이언트에서 가져온 후 백엔드에 전달
    @Override
    public String getFcmToken() {
        // 안드로이드에서 기기번호 가져오는 코드
        return "";
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
