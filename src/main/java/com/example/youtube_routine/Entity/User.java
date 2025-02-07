package com.example.youtube_routine.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String deviceId; // 디바이스 고유 식별자 -> 따로 id/pw X

    //하나의 사용자는 여러개의 루틴 가질수 있음
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Routine> routines; // 사용자별 루틴 설정 리스트

    public User() {

    }
}
