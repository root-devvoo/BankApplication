# BankApplication

> **Junit Bank App**

> 과제 수행 겸 공부를 위해 [인프런 : 스프링부트 JUnit 테스트 - 시큐리티를 활용한 Bank 애플리케이션](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81%EB%B6%80%ED%8A%B8-junit-%ED%85%8C%EC%8A%A4%ED%8A%B8/dashboard) 강의를 들으며 회원가입, 계좌 목록, 입출금 내역, 잔고 확인이 가능한 Bank 애플리케이션을 개발.

## 💻 개발환경

- Java 11

- SpringBoot : 2.7.11

  > SpringBoot Security(JWT Token), Spring AOP

- Gradle : 7.6.1

- JPA

  > MariaDB



- 테스트 도구

  > JUnit, Postman

---

## 📒Note

### Jpa LocalDateTime 자동으로 생성하는 법
- @EnableJpaAuditing (Main 클래스)
- @EntityListeners(AuditingEntityListener.class) (Entity 클래스)
```java
    @CreatedDate // Insert
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // Insert, Update
    @Column(nullable = false)
    private LocalDateTime updatedAt;
```