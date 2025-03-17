package com.example.youtube_routine.User;

public class UserRepositoryTest {
}
/*
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("deviceId로 사용자 조회 성공")
    void findByDeviceId_success() {
        // given
        String deviceId = "unique-device-id";
        User user = User.builder().deviceId(deviceId).build();
        userRepository.save(user);

        // when
        Optional<User> foundUser = userRepository.findByDeviceId(deviceId);

        // then
        assertTrue(foundUser.isPresent());
        assertEquals(deviceId, foundUser.get().getFcmToken());
    }

    @Test
    @DisplayName("존재하지 않는 deviceId로 사용자 조회 실패")
    void findByDeviceId_notFound() {
        // given
        String deviceId = "non-existent-device-id";

        // when
        Optional<User> foundUser = userRepository.findByDeviceId(deviceId);

        // then
        assertFalse(foundUser.isPresent());
    }
}

 */
