package com.example.youtube_routine.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화 (POST 요청 가능)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 X
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/**").permitAll()  // 회원가입 엔드포인트 인증 없이 허용
                        .requestMatchers("/api/notifications/**").permitAll() // 푸시 알림 관련 API 허용
                        .requestMatchers("api/routines/**").permitAll() // 루틴 api 허용
                        .anyRequest().authenticated()  // 그 외 모든 요청은 인증 필요
                )
                .httpBasic(httpBasic -> httpBasic.disable())  // 기본 인증 비활성화
                .formLogin(form -> form.disable()); // 폼 로그인 비활성화

        return http.build();
    }
}
