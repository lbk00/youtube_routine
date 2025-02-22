package com.example.youtube_routine.User;

public interface UserService {
    // fcmToken 을 가져오는 메서드
    String getFcmToken();
    //fcmToken 전달 받고 사용자 객체 생성
    UserResponseDTO registerUser(String fcmToken);
    // fcmToken 으로 사용자 조회
    UserResponseDTO getUser(String fcmToken);
    // fcmToken 으로 사용자 삭제
    void deleteUser(String fcmToken);

}
