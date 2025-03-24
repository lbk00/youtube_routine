package com.example.youtube_routine.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByFcmToken(String fcmToken);
    void deleteByFcmToken(String fcmToken);
}
