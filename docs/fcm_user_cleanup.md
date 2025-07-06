## Flutter에서 앱이 삭제시 사용자 정보 삭제

**문제 상황**

> **Flutter(Android/iOS)는 앱이 삭제되는 것을 감지할 수 있는 공식적인 방법이 없음**

**방안 1 : 앱 내에서 "회원 탈퇴" 기능 만들기**

- 사용자가 앱 내에서 "탈퇴" 버튼을 누르면  API 호출
- 이 방법의 문제점 : 사용자가 그냥 앱을 삭제하면,  백엔드에 사용자 정보, FCM 토큰, 루틴 데이터가 그대로 남아있음 → 서버는 계속 푸시 알림 보냄 (실패하긴 하지만 **자원 낭비**)

<aside>

**방안 2: 서버에서 FCM 토큰 유효성 체크 후 삭제**

</aside>

- 현재 프로젝트에서는 스케줄러로 fcm 토큰을 사용해 주지적으로 푸시알림 전송
- FCM 푸시 실패 응답을 서버에서 감지해서 사용자/루틴 삭제

> **기존 코드**
> 

```java
// 모든 조건 통과
log.info("FCM 전송 시작");
// FCM 푸시 알림 전송
sendPushNotification(fcmToken, routine);
```

> **수정된 코드**
> 

```java
          	// 모든 조건 통과
            log.info("FCM 전송 시작");

            // FCM 전송 시도 및 실패 감지시 사용자 삭제
            try {
                sendPushNotification(fcmToken, routine);
            } catch (FirebaseMessagingException e) {
                log.error("[FCM 전송 실패] 루틴 ID: {}, 이유: {}", routine.getId(), e.getMessage(), e);

                if (e.getMessagingErrorCode() == MessagingErrorCode.UNREGISTERED) {
                    userRepository.deleteByFcmToken(fcmToken); // 사용자 삭제
                    log.info("FCM 토큰 무효 → 사용자 삭제 완료");
                }

                continue;
            }
```

<aside>

**사용자가 루틴을 모두 삭제하고 앱까지 삭제했다면, 서버는 이 사용자에게 푸시를 보낼 일이 없음**

**→ 에러도 안 나고, 결국 이 유저는 DB에 남음 , 어떻게 처리?**

</aside>

- 루틴이 하나도 없고 + 마지막 사용이 오래된 유저 삭제

> **User 엔티티에 lastActiveAt 필드 추가**
> 

```java
@Column(nullable = false)
private LocalDateTime lastActiveAt; // 루틴을 다루는 활성화된 시간
```

- lastAciveAt 필드는 사용자 등록 / 루틴 생성,수정,삭제 / 토글 버튼 사용 의 활동으로 갱신됨
- `user.setLastActiveAt(LocalDateTime.*now*()); // 활동 시간 갱신`

> **하루에 한 번 비활성 사용자를 탐색하는 스케줄러 생성**
> 

```java
@Service
@RequiredArgsConstructor
public class UserScheduler {
    private final UserRepository userRepository;

    // 매일 새벽 3시에 실행
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void deleteInactiveUsers() {
        List<User> allUsers = userRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (User user : allUsers) {
            boolean isInactive = user.getLastActiveAt().isBefore(now.minusDays(30)); // 30일 이상 활동 없는 사용자

            if (isInactive) {
                userRepository.delete(user);
                log.info("30일 이상 활동 없는 사용자 삭제: {}", user.getFcmToken());
            }
        }
    }
}
```

<aside>

**수정된 방식**

</aside>

- **루틴 존재하는 상태에서 + 앱 삭제** → FCM 실패시 사용자 삭제
- **루틴 모두 삭제하고 + 앱 삭제** → 스케줄러로 30일동안 활동없으면 사용자 삭제

> 오류로 유효성 체크 오류나면 , 루틴 다 삭제되는거 아닌가?
> 

**→ 드물게 발생할수 있는 케이스지만 운영 안정성과 데이터 보호를 모두 고려하려면…**

- User 엔티티에 isActive 필드 추가 ,  FCM 토큰 유효성 실패시 isActive 필드 false
- 동시에 lastActiveAt 필드를 추가하여, 루틴 생성·수정·삭제 등 활동이 있을 때마다 갱신
- 루틴 crud 동작하면 isActive 필드 true로,
- UserScheduler 로 한달 이상 LastActiveAt 이 30일 이상 차이나고, isActive가 false인 사용자 삭제

<aside>

> **최종 코드**

</aside>

- User에 isActive 필드 추가
- FCM 전송 시도 및 실패 감지시   isActive = false; ( 사용자 비활성화 )
- 사용자 등록 / 루틴 생성,수정,삭제 / 토글 버튼 사용시  isActive =  true;
- `user.setLastActiveAt(LocalDateTime.*now*()); + user.setActive(true);`

> `RoutineScheduler → checkRoutineNotifications()`
> 

```java
            // FCM 전송 시도 및 실패 감지시 사용자 isActive = false
            // UserScheduler가 30일 이후 자동 삭제
            try {
                sendPushNotification(fcmToken, routine);
            } catch (FirebaseMessagingException e) {
                log.error("[FCM 전송 실패] 루틴 ID: {}, 이유: {}", routine.getId(), e.getMessage(), e);

                if (e.getMessagingErrorCode() == MessagingErrorCode.UNREGISTERED) {
                    User user = routine.getUser();

                    user.setActive(false); // 비활성화 마킹
                    userRepository.save(user);
                    log.info("FCM 토큰 무효 → 사용자 isActive=false 처리 완료");
                }

                continue;
            }
```

> `UserScheduler`
>
```java
@Service
@RequiredArgsConstructor
public class UserScheduler {

    private final UserRepository userRepository;

    // 매일 새벽 3시에 실행
    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    public void deleteInactiveUsers() {
        List<User> allUsers = userRepository.findAll();
        LocalDateTime now = LocalDateTime.now();

        for (User user : allUsers) {
            boolean isInactive = !user.isActive(); // 비활성 사용자
            boolean isOver30Days = user.getLastActiveAt().isBefore(now.minusDays(30)); // 30일 경과

            if (isInactive && isOver30Days) {
                userRepository.delete(user);
                log.info("30일 이상 활동 없는 사용자 삭제: {}", user.getFcmToken());
            }
        }
    }
}
```
