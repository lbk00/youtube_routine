package com.example.youtube_routine.Routine.DTO;

import com.example.youtube_routine.Routine.Entity.Day;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

// 새로운 루틴을 만들기위해 필요한 DTO
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoutineRequestDTO {
    private List<Day> days = List.of(); // 요일
    private String routineTime;
    private String youtubeLink;
    private String content; // 메시지
    private boolean repeatFlag;
}

