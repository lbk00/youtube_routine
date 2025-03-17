package com.example.youtube_routine.User;

public class UserControllerTest {
}
/*
@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("POST /api/users/register - 사용자 등록 성공")
    void registerUser_success() throws Exception {
        // given
        String deviceId = "unique-device-id";
        User user = User.builder().deviceId(deviceId).build();

        when(userService.registerUser(deviceId)).thenReturn(user);

        // when & then
        mockMvc.perform(post("/api/users/register/{deviceId}", deviceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceId").value(deviceId));
    }

    @Test
    @DisplayName("GET /api/users/{deviceId} - 사용자 조회 성공")
    void getUserByDeviceId_success() throws Exception {
        // given
        String deviceId = "unique-device-id";
        User user = User.builder().deviceId(deviceId).build();

        when(userService.getUserByDeviceId(deviceId)).thenReturn(user);

        // when & then
        mockMvc.perform(get("/api/users/{deviceId}", deviceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceId").value(deviceId));
    }
}

 */
