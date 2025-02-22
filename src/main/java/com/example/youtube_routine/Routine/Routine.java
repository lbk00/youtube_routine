package com.example.youtube_routine.Routine;

import com.example.youtube_routine.User.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "routines")
@Getter
@Setter
@NoArgsConstructor
public class Routine { // 사용자마다 최대 10개? 15개정도 제한되도록 조건 추가해야함
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 루틴 별 고유 id

    @Column(nullable = false)
    private Day day; // 요일

    @Column(nullable = false)
    private String routineTime; // 알람 시간

    @Column(nullable = false)
    private String youtubeLink; // 유튜브 링크

    @Column(nullable = false)
    private String content; // 푸시 알람으로 보여줄 텍스트 설정 , 간단한 설명 ex) 저녁 운동 , 아침 스트레칭

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 알람을 설정한 사용자

    @Column(nullable = false)
    private boolean repeatFlag; // 해당 루틴을 반복 수행할지 확인 , true = 반복 / false = 반복X

    @Column(nullable = false)
    private boolean isActive; // 토글버튼으로 루틴을 켜고 끌지 확인 , true = 켜짐 / false = 꺼짐

    @Builder
    public Routine(Long id, Day day, String routineTime, String youtubeLink, String content, User user , boolean repeatFlag) {
        this.id = id;
        this.day = day;
        this.routineTime = routineTime;
        this.youtubeLink = youtubeLink;
        this.content = content;
        this.user = user;
        this.repeatFlag = repeatFlag;
        this.isActive = true; // 기본 생성값 true
    }

}
