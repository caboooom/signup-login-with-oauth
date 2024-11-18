### signup-login-with-oauth 🔐

- 프론트엔드와 백엔드 서버가 분리된 환경에서 아래 기능을 아주아주 간단하게 구현해보았습니다.
  
  - 일반 회원가입, 일반 로그인
    
  - Google OAuth2 회원가입, Google OAuth2 로그인

<br>


### 프로젝트 구조

```plaintext
signup-login-with-oauth
├── frontservice/      # 프론트엔드 서버 (UI 페이지)
├── userservice/       # 백엔드 서버 (Spring Security)
└── README.md
```

- front-service : 사용자에게 간단한 UI 페이지 제공, OAuth 인증 요청을 백엔드로 전달
  - Java 17, Gradle, Spring Boot 3.2.11
    
- user-service : Spring Security를 사용하여 회원가입 및 로그인 요청 처리
  - Java 17, Gradle, Spring Boot 3.2.11, Spring Security 6.2.11, OAuth2 Client, H2 Database, JPA


