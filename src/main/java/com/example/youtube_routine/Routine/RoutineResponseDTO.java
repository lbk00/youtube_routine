package com.example.youtube_routine.Routine;

import lombok.Data;

import java.time.LocalTime;

// 루틴 정보 보여주는 DTO
@Data
public class RoutineResponseDTO {
    private Day day;
    private LocalTime routineTime;
    private String youtubeLink;
    private String content; // 메시지
    private boolean repeatFlag;

    public RoutineResponseDTO(Day day, LocalTime routineTime, String youtubeLink, String content, boolean repeatFlag) {
        this.day = day;
        this.routineTime = routineTime;
        this.youtubeLink = youtubeLink;
        this.content = content;
        this.repeatFlag = repeatFlag;
    }
}

