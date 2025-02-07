package com.example.youtube_routine.Repository;

import com.example.youtube_routine.Entity.Routine;
import com.example.youtube_routine.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoutineRepository extends JpaRepository<Routine, Long> {
    List<Routine> findByUser(User user);
}
