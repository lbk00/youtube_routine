package com.example.youtube_routine.Service;

import com.example.youtube_routine.Entity.User;
import com.example.youtube_routine.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User registerUser(String deviceId) {
        return userRepository.findByDeviceId(deviceId)
                .orElseGet(() -> userRepository.save(
                        User.builder()
                                .deviceId(deviceId)
                                .build()));
    }

    @Override
    public User getUserByDeviceId(String deviceId) {
        return userRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
