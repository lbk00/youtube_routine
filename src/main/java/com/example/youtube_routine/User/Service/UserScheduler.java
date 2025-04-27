package com.example.youtube_routine.User.Service;


import com.example.youtube_routine.User.Entity.User;
import com.example.youtube_routine.User.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserScheduler {

    private final UserRepository userRepository;

    // 매일 새벽 3시에 실행
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void deleteInactiveUsers() {
        List<User> allUsers = userRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (User user : allUsers) {
            boolean isInactive = !user.isActive(); // 비활성 사용자
            boolean isOver30Days = user.getLastActiveAt().isBefore(now.minusDays(30)); // 30일 경과

            if (isInactive && isOver30Days) {
                userRepository.delete(user);
//                System.out.println("30일 이상 비활성 사용자 삭제됨 → FCM: " + user.getFcmToken());
            }
        }
    }
}