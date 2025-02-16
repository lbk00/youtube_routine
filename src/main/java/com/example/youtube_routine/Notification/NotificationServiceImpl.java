package com.example.youtube_routine.Notification;


import com.example.youtube_routine.Routine.Routine;
import com.example.youtube_routine.Routine.RoutineRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService{
    private final RoutineRepository routineRepository;

    @Override
    public void sendNotification(Long routineId) {
        // FCM 푸시 알림 전송 로직 (Firebase 연동)
    }

    @Override
    public String getYoutubeLink(Long routineId) {
        Routine routine = routineRepository.findById(routineId)
                .orElseThrow(() -> new EntityNotFoundException("Routine not found"));
        return routine.getYoutubeLink();
    }
}
