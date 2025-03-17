package com.example.youtube_routine.User;

public class UserServiceTest {
}
/*
@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("사용자 등록 - 새로운 사용자")
    void registerUser_newUser_success() {
        // given
        String deviceId = "unique-device-id";
        when(userRepository.findByDeviceId(deviceId)).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(User.builder().deviceId(deviceId).build());

        // when
        User user = userService.registerUser(deviceId);

        // then
        assertNotNull(user);
        assertEquals(deviceId, user.getFcmToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    @DisplayName("사용자 조회 - 존재하지 않는 사용자")
    void getUserByDeviceId_userNotFound_throwsException() {
        // given
        String deviceId = "non-existent-device-id";
        when(userRepository.findByDeviceId(deviceId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(EntityNotFoundException.class, () -> userService.getUserByDeviceId(deviceId));
    }
}

 */