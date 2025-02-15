package com.example.youtube_routine.Controller;

import com.example.youtube_routine.Entity.User;
import com.example.youtube_routine.Service.UserService;
import com.example.youtube_routine.Service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    // 사용자는 띠로 id/ pw 없이 로그인 가능
    // 사용자의 기기의 고유번호로 사용자 엔티티 생성

    private final UserService userService;

    // api
    // 1. 앱을 처음 실행했을때, 고유 디바이스번호로 사용자 객체 생성 post
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<User> register(@RequestBody String deviceId) {
//        String findDeviceId = userService.findDeviceId();
        // 사용자별로 디바이스 id 찾아서 반환
        // 그 후, deviceId로 사용자 등록
        User user = userService.registerUser(deviceId);
        return ResponseEntity.ok(user);
    }

    // 사용자 조회 api (deviceId로 조회)
    @GetMapping("/{deviceId}")
    public ResponseEntity<User> getUserByDeviceId(@PathVariable String deviceId) {
        User user = userService.getUser(deviceId);
        return ResponseEntity.ok(user);
    }

    // 사용자 삭제 api (앱 삭제 시 호출)
    @DeleteMapping("/{deviceId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String deviceId) {
        userService.deleteUser(deviceId); // 사용자 및 관련 데이터 삭제
        return ResponseEntity.noContent().build();
    }
}
