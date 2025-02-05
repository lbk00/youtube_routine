package com.example.youtube_routine.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;

@Entity
@Data
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalTime routineTime; // 알람 시간

    @Column(nullable = false)
    private String youtubeLink; // 유튜브 링크

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 알람을 설정한 사용자

    //알림
}
