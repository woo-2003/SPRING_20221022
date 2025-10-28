**자바웹프(2)_중간점검_20221022_정우영**
## 1. 전체 실습과정 완료
- 기본 기능 동작.
- 2-1주차[스프링 부트 개발환경 완료]
- 2-2주차[컨트롤러 입문]
- 3주차[포트폴리오템플릿 작성]
- 4주차: [MYSQl 연동 및 작성]
- 5주차: [블로그 게시판 조회, 글쓰기 구현]
- 6주차: [블로그 게시판 수정 ,삭제 구현]

## 2. 연습문제(모두 완료)
- 2주차: URL 맵핑과 컨트롤러 추가하기
  - DemoController.java에 /hello2 맵핑을 추가하고, Model을 사용해 5개의 데이터(data1~data5)를 View로 전달

```java
// DemoController.java
@GetMapping("/hello2") // 전송 방식 GET
public String hello2(Model model) {
  model.addAttribute("data1", " 홍길동님."); // model 설정
  model.addAttribute("data2", " 방갑습니다.");
  model.addAttribute("data3", " 오늘.");
  model.addAttribute("data4", " 날씨는.");
  model.addAttribute("data5", " 매우 좋습니다.");
  return "hello2"; // hello2.html 연결
}
```

  - hello2.html에서 Thymeleaf의 th:text 속성을 이용해 컨트롤러가 보낸 5개의 데이터를 출력

```html
<p th:text="${data1}"></p>
<p th:text="${data2}"></p>
<p th:text="${data3}"></p>
<p th:text="${data4}"></p>
<p th:text="${data5}"></p>
<a href="index/">홈페이지 메인</a>
```

- 3주차: [상세 페이지 수정하기 (창 닫기 기능)] 
  - about_detailed.html의 '되돌아가기' 버튼과 '창닫기' 버튼 추가, onclick="confirmClose()" 추가
  - DemoController.java에 맵핑 추가

```java
@GetMapping("/index")
    public String index() {
    return "index";
    }
```

  - 되돌아가기 기능 추가

```html
<a class="btn btn-primary py-3 px-5" href="/index">되돌아가기</a>
```

  - static/js/main.js 파일에 confirmClose() 함수 추가

```javascript
function confirmClose() {
    if (confirm("정말 창을 닫으시겠습니까?")) {
        window.close(); 
    }
}
```

  - 창닫기 기능

```html
<a href="#" onclick="confirmClose()" class="btn btn-primary py-3 px-5">창닫기</a>
```

- 4주차: [데이터베이스 연동 설정] 
  - VS Code MySQL 확장을 사용하여 testdb 테이블에 사용자 2명(아저씨, 꾸러기) INSERT 완료
  - TestService.java에 findAllUsers() 메서드를 추가하여 testRepository.findAll() 호출
  - DemoController.java의 /testdb 맵핑을 수정하여 findAllUsers() 결과를 Model에 "users"라는 이름으로 추가
  - testdb.html을 수정하여 Thymeleaf th:each 반복문을 사용해 users 리스트의 모든 사용자 ID와 이름을 출력

- 5주차: [페이지 리다이렉트]
  - 글쓰기 처리 방식을 @Controller 방식으로 변경.
  - BlogController.java에 @PostMapping("/api/articles") 메서드 추가 .
  - 게시글 저장(blogService.save()) 후, "redirect:/article_list"를 반환하여 목록 페이지로 자동 이동(리다이렉트) 구현.
  - 충돌 방지를 위해 BlogRestController.java의 @PostMapping("/api/articles") 메서드 주석 처리.

- 5주차: [추가 - 예외처리]
  - URL 경로의 ID 파라미터 타입 불일치(MethodArgumentTypeMismatchException) 시 커스텀 에러 페이지를 보여주도록 구현 .
  - exception 패키지 생성 및 GlobalExceptionHandler.java 파일 추가.
  - @ControllerAdvice 어노테이션을 사용하여 전역 예외 처리기 등록.
  - @ExceptionHandler(MethodArgumentTypeMismatchException.class) 어노테이션을 사용하여 특정 예외 처리 메서드 지정.
  - 타입 불일치 예외 발생 시 보여줄 새로운 에러 페이지(error_page/error_type_mismatch.html) 생성 및 반환.

## 3. 홈페이지 내용 수정
- 텍스트,디자인 등
- 메뉴 수정: 네비게이션 바 메뉴 한글화 (3주차) . 
- 프로필 이미지 src="img/profile2.png(AI 본인 프로필사진) 으로 교체 (3주차). 
- 내소개(About)' 텍스트 수정 (3주차) . 
- 기술 & 경험(Skills & Expertise) 섹션 텍스트 한글 수정 (4주차) . 
- Thymeleaf 테스트 페이지 (thymeleaf_test1.html) 생성 및 th:utext, th:value, th:field, th:href, th:with 문법 실습 (4주차)
- 게시판 링크 추가: index.html 네비게이션 바 '게시판' 메뉴에 /article_list 링크 추가 (5주차) .
- 게시판 목록 수정/삭제 버튼 추가 (6주차) .

## 4. 소스 코드 정리
- 각 파일의 역할 설명 (실제 코드 내용 기반)

- 루트/빌드
  - pom.xml: Spring Boot 3.5.7, Java 21, Web/Thymeleaf/JPA/Actuator, Lombok, Devtools, MySQL Connector 의존성 및 플러그인 설정.
  - mvnw, mvnw.cmd: Maven Wrapper 실행 스크립트.

- Java (src/main/java)
  - DemoApplication.java: 애플리케이션 진입점. `SpringApplication.run(...)`으로 부트스트랩.
  - controller/BlogController.java: 게시판 화면 컨트롤러.
    - `GET /article_list`: 게시글 목록 조회 후 `article_list.html` 렌더.
    - `GET /article_edit/{id}`: 단건 조회 후 수정 화면 렌더(없으면 오류 페이지로 분기).
    - `POST /api/articles`: 글 저장 후 목록으로 리다이렉트.
    - `PUT /api/article_edit/{id}`: 글 수정 처리 후 목록으로 리다이렉트.
    - `DELETE /api/article_delete/{id}`: 글 삭제 처리 후 목록으로 리다이렉트.
  - controller/BlogRestController.java: REST 관련 보조 컨트롤러.
    - `GET /favicon.ico`: 빈 처리(불필요한 에러 방지). 주석 처리된 REST 예제 포함.
  - controller/DemoController.java: 실습용 화면 컨트롤러.
    - `GET /hello`, `/hello2`: 모델 데이터 전달 및 템플릿 렌더.
    - `GET /about_detailed`, `/index`: 포트폴리오/메인 페이지 연결.
    - `GET /test1`: Thymeleaf 문법 실습 페이지 연결.
    - `GET /testdb`: DB 사용자 목록 조회(Model에 `users`) 후 `testdb.html` 렌더.
  - exception/GlobalExceptionHandler.java: 전역 예외 처리.
    - `MethodArgumentTypeMismatchException` 발생 시 `error_page/error_type_mismatch` 반환.
  - model/domain/Article.java: 게시글 JPA 엔티티(id, title, content). `@Builder`, `update(title, content)` 제공.
  - model/domain/TestDB.java: 예제 JPA 엔티티(id, name).
  - model/repository/BlogRepository.java: `Article`용 `JpaRepository<Article, Long>`.
  - model/repository/TestRepository.java: `TestDB`용 `JpaRepository<TestDB, Long>` + `findByName(String)`.
  - model/service/AddArticleRequest.java: 게시글 생성 DTO. `toEntity()`로 `Article` 빌드.
  - model/service/BlogService.java: 게시판 비즈니스 로직. `findAll`, `save`, `findById`, `update`, `delete` 제공.
  - model/service/TestService.java: 사용자 조회 비즈니스. `findByName`, `findAllUsers` 제공.

- Resources (src/main/resources)
  - application.properties: 앱 이름, 정적 리소스 경로, MySQL 접속 정보, JPA 설정(ddl-auto=update, show-sql), HiddenHttpMethodFilter 활성화.
  - templates/article_list.html: 게시글 목록 테이블 + 글 추가 폼. 수정/삭제 UI와 폼 전송 포함.
  - templates/article_edit.html: 게시글 수정 폼 화면.
  - templates/error_page/error_type_mismatch.html: 잘못된 파라미터 타입 접근 시 표시하는 에러 페이지.
  - templates/hello.html: `hello` 화면 템플릿.
  - templates/hello2.html: 5개 모델 속성(data1~data5)을 `th:text`로 출력.
  - templates/about_detailed.html: 상세 페이지. 되돌아가기/창닫기 동작을 위한 버튼 사용.
  - templates/index.html, templates/index1.html: 메인/샘플 인덱스 페이지 템플릿.
  - templates/testdb.html: `users` 리스트를 `th:each`로 순회하여 ID/이름 출력.
  - templates/thymeleaf_test1.html: `th:utext`, `th:value`, `th:field`, `th:href`, `th:with` 등 문법 실습 페이지.
  - static/js/main.js: UI 보조 스크립트. `confirmClose()` 등 프론트 동작 정의.
  - static/css/bootstrap.min.css, static/css/style.css: 스타일시트.
  - static/img/*: 페이지에서 사용하는 이미지 리소스.
  - static/lib/*: 외부 프론트 라이브러리(animate, counterup, easing, isotope, lightbox, owlcarousel, typed, waypoints, wow 등) 정적 파일.
  - static/scss/*: Bootstrap SCSS 소스(컴포넌트/유틸리티/믹스인 포함).

- Test (src/test/java)
  - DemoApplicationTests.java: 기본 테스트 스캐폴딩.
