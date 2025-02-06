package com.example.youtube_routine.Repository;

import com.example.youtube_routine.Entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineRepository extends JpaRepository<Routine, Integer> {
}
