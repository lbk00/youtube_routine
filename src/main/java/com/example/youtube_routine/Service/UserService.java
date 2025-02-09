package com.example.youtube_routine.Service;

import com.example.youtube_routine.Entity.User;

public interface UserService {
    //디바이스 번호 받고 사용자 객체 생성
    User registerUser(String deviceId);
    // 디바이스 번호로 사용자 조회
    User getUserByDeviceId(String deviceId);

}
