package com.example.youtube_routine.Routine;

import lombok.Data;

import java.time.LocalTime;

// 새로운 루틴을 만들기위해 필요한 DTO
@Data
public class RoutineRequestDTO {
    private Day day;
    private LocalTime routineTime;
    private String youtubeLink;
    private String content; // 메시지
    private boolean repeatFlag;
}

