package com.example.youtube_routine.User.DTO;


import com.example.youtube_routine.Routine.DTO.RoutineResponseDTO;
import com.example.youtube_routine.User.Entity.User;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserResponseDTO {
    private String fcmToken;
    private List<RoutineResponseDTO> routines;

    public UserResponseDTO(User user) {
        this.fcmToken = user.getFcmToken();
        this.routines = user.getRoutines().stream()
                .map(RoutineResponseDTO::new)
                .collect(Collectors.toList());
    }
}
