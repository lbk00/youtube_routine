package com.example.youtube_routine.Entity;

import lombok.Data;

import java.time.LocalTime;

// 새로운 루틴을 만들기위해 필요한 DTO
@Data
public class RoutineRequestDTO {
    private String deviceId; // 유지 (핸드폰 고유 식별자)
    private Day day;
    private LocalTime routineTime;
    private String youtubeLink;
    private String content; // 메시지
}

