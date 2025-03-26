package com.example.youtube_routine.User;

import com.example.youtube_routine.Routine.Routine;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String fcmToken; // 디바이스 고유 식별자 -> 따로 id/pw X

    @Column(nullable = false)
    private boolean isActive = true; // 사용자 활성화 여부

    @Column(nullable = false)
    private LocalDateTime lastActiveAt; // 루틴을 다루는 활성화된 시간

    //하나의 사용자는 여러개의 루틴 가질수 있음
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Routine> routines; // 사용자별 루틴 설정 리스트

    @Builder
    public User(String fcmToken, List<Routine> routines, boolean isActive , LocalDateTime lastActiveAt) {
        this.fcmToken = fcmToken;
        this.routines = routines;
        this.isActive = isActive;
        this.lastActiveAt = lastActiveAt;
    }

}
