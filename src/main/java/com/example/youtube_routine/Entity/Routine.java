package com.example.youtube_routine.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import java.time.LocalTime;


@Data
@Builder
public class Routine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 루틴 별 고유 id

    @Column(nullable = false)
    private Day day; // 요일

    @Column(nullable = false)
    private LocalTime routineTime; // 알람 시간

    @Column(nullable = false)
    private String youtubeLink; // 유튜브 링크

    @Column(nullable = false)
    private String content; // 푸시 알람으로 보여줄 텍스트 설정 , 간단한 설명 ex) 저녁 운동 , 아침 스트레칭

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 알람을 설정한 사용자

    //알림
}
