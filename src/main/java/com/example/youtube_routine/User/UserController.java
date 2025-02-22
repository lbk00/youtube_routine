package com.example.youtube_routine.User;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    // 사용자는 띠로 id/ pw 없이 로그인 가능
    // 사용자의 fcmToken을 받아서 사용자 객체 생성

    private final UserService userService;

    // api
    // 1. 앱을 처음 실행했을때, 고유 디바이스번호로 사용자 객체 생성 post
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRequestDTO request) {

        // 사용자별로 fcmToken 찾아서 반환
        // 그 후, fcmToken 으로 사용자 등록
        UserResponseDTO userDTO = userService.registerUser(request.getFcmToken());
        return ResponseEntity.ok(userDTO);
    }

    // 사용자 조회 api (fcmToken 으로 조회)
    @GetMapping("/{fcmToken}")
    public ResponseEntity<UserResponseDTO> getUserByFcmToken(@PathVariable String fcmToken) {
        UserResponseDTO userDTO = userService.getUser(fcmToken);
        return ResponseEntity.ok(userDTO);
    }

    // 사용자 삭제 api (앱 삭제 시 호출)
    @DeleteMapping("/{fcmToken}")
    public ResponseEntity<Void> deleteUser(@PathVariable String fcmToken) {
        userService.deleteUser(fcmToken); // 사용자 및 관련 데이터 삭제
        return ResponseEntity.noContent().build();
    }
}
