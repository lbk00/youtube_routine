package com.example.youtube_routine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class YoutubeRoutineApplication {

    public static void main(String[] args) {
        SpringApplication.run(YoutubeRoutineApplication.class, args);
    }

}
