package com.example.youtube_routine.Routine.Entity;

import com.example.youtube_routine.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Entity
@Table(name = "routines")
@Getter
@Setter
@NoArgsConstructor
public class Routine { // 사용자마다 최대 10개? 15개정도 제한되도록 조건 추가해야함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 루틴 별 고유 id

    @Column(nullable = false, columnDefinition = "VARCHAR(255) DEFAULT ''")
    private String days;  // 요일

    @Column(nullable = false)
    private String routineTime; // 알람 시간

    @Column(nullable = false)
    private String youtubeLink; // 유튜브 링크

    @Column(nullable = false)
    private String content; // 푸시 알람으로 보여줄 텍스트 설정 , 간단한 설명 ex) 저녁 운동 , 아침 스트레칭

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 알람을 설정한 사용자

    @Column(nullable = false)
    private boolean repeatFlag; // 해당 루틴을 반복 수행할지 확인 , true = 반복 / false = 반복X

    @Column(nullable = false)
    private boolean isActive; // 토글버튼으로 루틴을 켜고 끌지 확인 , true = 켜짐 / false = 꺼짐

    @Builder
    public Routine(Long id, String days, String routineTime, String youtubeLink, String content, User user , boolean repeatFlag) {
        this.id = id;
        this.days = days;
        this.routineTime = routineTime;
        this.youtubeLink = youtubeLink;
        this.content = content;
        this.user = user;
        this.repeatFlag = repeatFlag;
        this.isActive = true; // 기본 생성값 true
    }

    // 요일 저장 시 List<Day> → String 변환
    public void setDays(List<Day> dayList) {
        if (dayList == null || dayList.isEmpty()) {
            this.days = ""; // NULL 방지: 빈 문자열로 저장
        } else {
            this.days = dayList.stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(","));
        }
    }

    // DB에서 꺼낼 때 String → List<Day> 변환
    public List<Day> getDaysList() {
        if (days == null || days.isEmpty()) {
            return List.of();
        }
        return Arrays.stream(days.split(","))
                .map(Day::valueOf)
                .collect(Collectors.toList());
    }


}
