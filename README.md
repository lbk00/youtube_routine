## 유튜브 알림 루틴 앱

### 프로젝트 개요
- 사용자가 등록한 시간에 맞춰 푸시 알림을 전송하고, 알림 클릭 시 저장된 유튜브 링크를 자동으로 실행하는 Android 앱
- 반복적인 운동 루틴, 명상 등 습관 형성을 위한 영상 시청을 도와주는 동기부여 목적의 앱

### 주요 기능 및 역할
- Spring Boot와 JPA를 활용하여 사용자 및 루틴의 CRUD 기능을 구현
- 앱 인스턴스를 식별하는 FCM Token 기반 사용자 관리 (회원가입 없이 사용 가능)
- 스케줄러를 통해 루틴 시간에 맞춰 자동으로 FCM 푸시 알림 전송
- FCM 연동으로 알림 클릭 시 유튜브 링크 자동 실행 처리 (Flutter + Android Native 연동)
- 사용자는 요일 및 시간, 유튜브 링크, 알림 메시지를 포함한 루틴을 자유롭게 생성 및 수정 가능

### 시연 영상
https://github.com/user-attachments/assets/5a5edbe2-1b0a-45c9-a245-bcc222bce4a0

### 기술 스택
- 백엔드 : Java, Spring Boot,JPA, MySQL
- 프론트엔드 : Flutter, Dart
- 인프라 : Firebase Cloud Messaging (FCM)
