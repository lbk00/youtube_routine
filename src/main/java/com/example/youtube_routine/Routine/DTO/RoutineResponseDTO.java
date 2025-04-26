package com.example.youtube_routine.Routine.DTO;

import com.example.youtube_routine.Routine.Entity.Day;
import com.example.youtube_routine.Routine.Entity.Routine;
import lombok.Data;

import java.util.List;

// 루틴 정보 보여주는 DTO
@Data
public class RoutineResponseDTO {
    private Long id;
    private List<Day> days;
    private String routineTime;
    private String youtubeLink;
    private String content;
    private boolean repeatFlag;
    private boolean isActive;

    public RoutineResponseDTO(Routine routine) {
        this.id = routine.getId();
        this.days = routine.getDaysList();  // String을 다시 List<Day>로 변환
        this.routineTime = routine.getRoutineTime();
        this.youtubeLink = routine.getYoutubeLink();
        this.content = routine.getContent();
        this.repeatFlag = routine.isRepeatFlag();
        this.isActive = routine.isActive();
    }
}


