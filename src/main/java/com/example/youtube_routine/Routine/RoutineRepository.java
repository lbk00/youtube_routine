package com.example.youtube_routine.Routine;

import com.example.youtube_routine.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
//    @Query("SELECT r FROM Routine r JOIN r.user u WHERE u.fcmToken = :fcmToken")
    // 사용자별 루틴 조회
    List<Routine> findByUserFcmToken(@Param("fcmToken") String fcmToken);
    // 특정 사용자에 대한 모든 루틴 삭제
    void deleteAllByUser(User user);
    // 사용자별 루틴 개수 조회
    long countByUser(User user);
    // 특정 시간의 모든 루틴 조회
    List<Routine> findByRoutineTime(String routineTime);
    // 사용자별 루틴 조회
    List<Routine> findByUser(User user);
    // days 컬럼에 현재 요일이 포함된 루틴 조회
//    @Query("SELECT r FROM Routine r WHERE r.routineTime = :routineTime AND r.days LIKE %:day%")
//    List<Routine> findByRoutineTimeAndDay(String routineTime, String day);
}
