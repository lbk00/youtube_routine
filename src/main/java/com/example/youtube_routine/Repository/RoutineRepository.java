package com.example.youtube_routine.Repository;

import com.example.youtube_routine.Entity.Routine;
import com.example.youtube_routine.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
    @Query("SELECT r FROM Routine r JOIN r.user u WHERE u.deviceId = :deviceId")
    List<Routine> findByUserDeviceId(@Param("deviceId") String deviceId); // 루틴에 deviceId 필드 추가 없이 조회
    // 특정 사용자에 대한 모든 루틴 삭제
    void deleteAllByUser(User user);

}
