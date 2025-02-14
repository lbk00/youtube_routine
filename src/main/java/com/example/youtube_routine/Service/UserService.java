package com.example.youtube_routine.Service;

import com.example.youtube_routine.Entity.User;

public interface UserService {
    // 디바이스 번호를 가져오는 메서드
    String getDeviceId();
    //디바이스 번호 받고 사용자 객체 생성
    User registerUser(String deviceId);
    // 디바이스 번호로 사용자 조회
    User getUser(String deviceId);
    // 디바이스 번호로 사용자 삭제
    void deleteUser(String deviceId);

}
