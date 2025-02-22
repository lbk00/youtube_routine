package com.example.youtube_routine.Routine;

import lombok.Data;

// 루틴 정보 보여주는 DTO
@Data
public class RoutineResponseDTO {
    private Day day;
    private String routineTime;
    private String youtubeLink;
    private String content; // 메시지
    private boolean repeatFlag;
    private boolean isActive;

    public RoutineResponseDTO(Routine routine) {
        this.day = routine.getDay();
        this.routineTime = routine.getRoutineTime();
        this.youtubeLink = routine.getYoutubeLink();
        this.content = routine.getContent();
        this.repeatFlag = routine.isRepeatFlag();
        this.isActive = routine.isActive();
    }
}

