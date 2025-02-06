package com.example.youtube_routine.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {
    // 사용자는 띠로 id/ pw 없이 로그인 가능
    // 사용자의 기기의 고유번호로 사용자 엔티티 생성

    // api
    // 1. 앱을 처음 실행했을때, 고유 디바이스번호로 사용자 객체 생성 post
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@RequestBody String deviceId) {
        // 디바이스 번호 받고 사용자 객체 생성
//        User user = userService.registerUser(deviceId);
        return "test";
    }

    // 2. 사용자가 새로운 루틴 생성 post

    // 3. 사용자 별 루틴 조회 get
    // id 또는 deviceId로 조회

    // 4. 사용자가 기존 루틴 수정 put

    // 5. 사용자가 기존 루틴 삭제 delete

    // 6. 사용자가 설정한 루틴의 시간이 되었을때 알림 푸시 + 간단한 메시지도 같이 나오게

    // 7. 사용자가 알림 누르면 바로 유튜브 링크 나오도록


}
