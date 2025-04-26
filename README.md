## 유튜브 알림 루틴 앱

## 프로젝트 개요
- 사용자가 등록한 시간에 맞춰 푸시 알림을 전송하고, 알림 클릭 시 저장된 유튜브 링크를 자동으로 실행하는 Android 앱
- 반복적인 운동 루틴, 명상 등 습관 형성을 위한 영상 시청을 도와주는 동기부여 목적의 앱


## 주요 기능 및 역할
- Spring Boot와 JPA를 활용하여 사용자 및 루틴의 CRUD 기능을 구현
- 앱 인스턴스를 식별하는 FCM Token 기반 사용자 관리 (회원가입 없이 사용 가능)
- 스케줄러를 통해 루틴 시간에 맞춰 자동으로 FCM 푸시 알림 전송
- FCM 연동으로 알림 클릭 시 유튜브 링크 자동 실행 처리 (Flutter + Android Native 연동)
- 사용자는 요일 및 시간, 유튜브 링크, 알림 메시지를 포함한 루틴을 자유롭게 생성 및 수정 가능


##  API 명세서
| API 종류 | 메서드 | URL | 설명 |
|----------|--------|-----|------|
| User     | POST   | `/api/users/register`            | 앱 최초 실행 시 사용자 등록 (fcmToken 기반) |
|     | GET    | `/api/users/{fcmToken}`          | fcmToken으로 사용자 조회 |
|      | PUT    | `/api/users/update-fcm`          | fcmToken 갱신 (기존 사용자 업데이트) |
|     | DELETE | `/api/users/{fcmToken}`          | 사용자 삭제 (앱 삭제 시 호출) |
| Routine  | POST   | `/api/routines/create/{fcmToken}`| 새로운 루틴 생성 (요일, 시간, 메시지, 링크, 반복 여부 입력) |
|   | GET    | `/api/routines/user/{fcmToken}`  | 특정 사용자(fcmToken)의 루틴 전체 조회 |
|   | PUT    | `/api/routines/{routineId}`      | 기존 루틴 수정 |
|   | DELETE | `/api/routines/{routineId}`      | 루틴 삭제 |
|   | PUT    | `/api/routines/toggle/{routineId}` | 루틴 ON/OFF 상태 토글 |

<br>

## 기능 흐름 및 시연 영상

<table>
  <tr>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/f91381bb-87ce-43a1-8bb3-7ffb3b356a87" width="300"/><br/>
      <sub>1. 사용자가 설정할 요일, 시간, 유튜브 링크를 입력하면 루틴이 생성됩니다.</sub>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/8aeaaab2-9e3a-478e-b275-0626ffcf74f3" width="300"/><br/>
      <sub>2. 생성된 루틴은 목록에서 쉽게 확인하고, ON/OFF, 수정, 삭제할 수 있습니다.</sub>
    </td>
    <td align="center">
      <img src="https://github.com/user-attachments/assets/c11a3112-fbe8-4fd5-8e63-afa22f33477e" width="300"/><br/>
      <sub>3. 설정된 시간에 푸시 알림이 도착하고, 클릭 시 유튜브 영상으로 연결됩니다.</sub>
    </td>
  </tr>
</table>


https://github.com/user-attachments/assets/7a532773-f3ea-4fe5-b508-70ea422c7ad1





## SW Architecture
![image](https://github.com/user-attachments/assets/56e4a382-489d-4447-ac71-ea1b9bdff914)


## 기술 스택
- 백엔드 : Java, Spring Boot, JPA, MySQL
- 프론트엔드 : Flutter, Dart
- 인프라 : Firebase Cloud Messaging (FCM)



## 트러블슈팅

- 앱 삭제 시 유령 사용자 및 FCM 토큰 처리 문제

> **문제 상황**

Flutter 앱은 사용자가 앱을 삭제했는지 감지할 수 없기 때문에  
서버에는 FCM 토큰과 사용자 정보가 남아 불필요한 푸시 알림이 계속 시도되는 문제 발생

> **해결 방안**

FCM 실패 응답을 기반으로 사용자 비활성화 처리 
-> 30일 이상 활동 없는 사용자 자동 삭제 로직 추가

- [상세 해결 과정](./docs/fcm_user_cleanup.md)
