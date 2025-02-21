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

    public RoutineResponseDTO(Routine routine) {
        this.day = routine.getDay();
        this.routineTime = routine.getRoutineTime();
        this.youtubeLink = routine.getYoutubeLink();
        this.content = routine.getContent();
        this.repeatFlag = routine.isRepeatFlag();
    }
}

