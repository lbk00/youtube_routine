package com.example.youtube_routine.Service;

import com.example.youtube_routine.Entity.User;
import com.example.youtube_routine.Repository.RoutineRepository;
import com.example.youtube_routine.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoutineRepository routineRepository;

    // 사용자가 어플을 최초 실행시 기기 고유번호릉 가져옴
    // 기기번호는 클라이언트에서 가져온 후 백엔드에 전달
    @Override
    public String getDeviceId() {
        return "";
    }

    @Override
    public User registerUser(String deviceId) {
        return userRepository.findByDeviceId(deviceId)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .deviceId(deviceId)
                                .build()));
    }

    @Override
    public User getUser(String deviceId) {
        return userRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    // 사용자 삭제
    public void deleteUser(String deviceId) {
        User user = userRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        // 해당 사용자의 루틴 삭제
        routineRepository.deleteAllByUser(user);
        // 사용자 삭제
        userRepository.delete(user);
    }

}
