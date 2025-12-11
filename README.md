# Peakle
### 쾌적한 클라이밍! - 클라이밍장 혼잡도 정보 제공 플랫폼
<img width="600" height="1350" alt="image" src="https://github.com/user-attachments/assets/c9f12177-45e2-458d-86f0-9cb867964d28" />


## 목차
1. [프로젝트 콘셉트](#프로젝트-콘셉트)
2. [주요 기능](#주요-기능)
3. [기술 스택](#기술-스택)
   - [Management Tool](#management-tool)
   - [IDE](#ide)
   - [Frontend](#frontend)
   - [Backend](#backend)
4. [프로젝트 구조](#프로젝트-구조)
   - [Frontend](#frontend-1)
   - [Backend](#backend-1)
5. [ERD](#erd)
6. [팀 구성원](#팀-구성원)

## 프로젝트 콘셉트
### 클라이밍장 혼잡도 조회 및 커뮤니티 플랫폼
    → 방문한 클라이밍장의 혼잡도를 평가하고, 집계된 실시간 혼잡도를 지도를 통해 확인할 수 있으며,
    리뷰와 게시판을 통해 다른 사용자들과 정보를 공유할 수 있는 커뮤니티 플랫폼입니다.

## 주요 기능
<img width="1725" height="603" alt="image" src="https://github.com/user-attachments/assets/6979811f-b92a-4ca6-bb3d-807db0604ae0" />

- **클라이밍장 정보 관리**
  - 클라이밍장 위치, 연락처, 영업 상태 등 상세 정보 제공
  - 카카오맵 API를 활용해 클라이밍장 혼잡도를 마커로 시각화

- **혼잡도 평가 및 조회**
  - 사용자들의 혼잡도 평가를 수집하고 실시간 혼잡도 제공
  - 캐싱을 통한 성능 최적화
  - 원활/보통/혼잡 3단계 평가 시스템

- **운동 기록**
  - 캘린더 기반 운동 기록 및 조회
  - 클라이밍장 이름, 푼 문제, 후기, 만족도 등 운동 기록 가능

- **커뮤니티 기능**
  - 게시판: 사용자들이 정보와 경험을 공유
  - 리뷰: 시설에 대한 상세 리뷰 작성
  - 댓글: 게시물에 대한 피드백 및 토론
  - 찜 기능: 자주 방문하는 클라이밍장을 찜하여 관리

## 기술 스택

### Management Tool

![git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
![gitlab](https://img.shields.io/badge/gitlab-FC6D26?style=for-the-badge&logo=gitlab&logoColor=white)

### IDE

![vscode](https://img.shields.io/badge/VSCode-0078d7?style=for-the-badge&logo=visual%20studio%20code&logoColor=white)
![intellij](https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge&logo=intellijidea&logoColor=white)
![postman](https://img.shields.io/badge/postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

### Frontend

![html](https://img.shields.io/badge/html-E34F26?style=for-the-badge&logo=html5&logoColor=white)
![css3](https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white)
![javascript](https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black)
![vue3](https://img.shields.io/badge/Vue-4FC08D?style=for-the-badge&logo=vue.js&logoColor=white)
![vite](https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white)
![pinia](https://img.shields.io/badge/Pinia-ffd859?style=for-the-badge&logo=pinia&logoColor=black)
![bootstrap](https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)
![axios](https://img.shields.io/badge/AXIOS-5A29E4?style=for-the-badge&logo=axios&logoColor=white)

### Backend

![java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=openjdk&logoColor=white)
![springboot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![mybatis](https://img.shields.io/badge/MyBatis-2C3E50?style=for-the-badge&logoColor=white)
![jwt](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=jwt&logoColor=white)
![mysql](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

## 프로젝트 구조

### Frontend
```
frontend/
├── public/                   # 정적 파일 (이미지, 아이콘)
│   ├── img/                  # 캐러셀, 마커, 메달, 로고 등
│   └── styles/               # 글로벌 CSS
│
├── src/
│   ├── App.vue               # 루트 컴포넌트
│   ├── main.js               # Vue 진입점 (Pinia 초기화)
│   │
│   ├── components/           # UI 컴포넌트
│   │   ├── common/           # 헤더, 푸터, 차트, 지도 오버레이 등
│   │   ├── facility/         # 시설 상세 정보
│   │   ├── home/             # 홈 섹션 (캐러셀, TOP5, 인기 시설 등)
│   │   ├── board/            # 게시판 (작성, 상세, 목록, 댓글 등)
│   │   ├── review/           # 리뷰 (작성, 조회, 수정 등)
│   │   ├── myPage/           # 마이페이지 (프로필, 내 게시물, 찜 목록 등)
│   │   ├── myRecord/         # 방문 기록 (작성, 조회, 수정)
│   │   └── user/             # 인증 (로그인, 회원가입, 캘린더)
│   │
│   ├── router/               # Vue Router 설정
│   ├── stores/               # Pinia 상태 관리 (persist)
│   │   ├── user.js
│   │   ├── facility.js
│   │   ├── board.js
│   │   ├── review.js
│   │   ├── like.js
│   │   └── myRecord.js
│   │
│   ├── views/                # 페이지 뷰 (홈, 지도, 게시판, 마이페이지, 방문 기록)
│   ├── assets/               # 글로벌 CSS, 폰트
│   ├── index.html
│   ├── package.json
│   └── vite.config.js
│
└── .env.development          # 환경 변수 (Kakao API Key)
```

### Backend
```
backend/
├── mvnw / mvnw.cmd           # Maven Wrapper 실행 스크립트
├── pom.xml                   # Maven 빌드 설정 및 의존성
│
└── src/
    ├── main/
    │   ├── java/com/ssafy/mvc/
    │   │   ├── SsafitApplication.java            # Spring Boot 진입점
    │   │   ├── config/                           # 설정 클래스 (DB, CORS)
    │   │   ├── controller/                       # REST 컨트롤러 (6개)
    │   │   ├── model/
    │   │   │   ├── dto/                          # 데이터 전송 객체 (12개)
    │   │   │   ├── dao/                          # MyBatis DAO (8개)
    │   │   │   └── service/                      # 비즈니스 로직 (8개 인터페이스 + 구현체)
    │   │   ├── jwt/                              # JWT 토큰 관리 (4시간 유효)
    │   │   └── interceptor/                      # 인터셉터
    │   │
    │   └── resources/
    │       ├── application.properties            # Spring 설정
    │       ├── Schema.sql                        # DB 스키마 (테스트 데이터 포함)
    │       └── mappers/                          # MyBatis XML 매퍼 (8개)
    │
    └── test/
        └── java/com/ssafy/mvc/                   # 단위 테스트
```

## ERD
<img width="940" height="747" alt="image" src="https://github.com/user-attachments/assets/43c6965d-30d2-48d4-99bc-e044eb740154" />


## 팀 구성원

| 역할   | 이름     | 담당 업무                          |
| ------ | -------- | --------------------------------- |
| **FrontEnd/BackEnd** |  고대권  | 유저 및 게시판 CRUD 구현, 운동 기록 기능 |
| **FrontEnd/BackEnd** |  박성근  | 혼잡도 집계 및 조회 로직 구현, 클라이밍장 상세정보 CRUD 구현, KakaoMap API 기반 지도 및 혼잡도 시각화 |
