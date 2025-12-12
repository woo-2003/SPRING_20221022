# 자바 웹프로그래밍(2) 기말레포트

## 프로젝트 개요

- **개발자**: 20221022 정우영
- **개발 기간**: 2025.09 ~ 2025.12 (총 15주)
- **과목**: 성결대학교 "자바 웹 프로그래밍(2)" 강의 실습

### 프로젝트 설명

**Spring Boot를 기반으로 한 개인 포트폴리오 소개 사이트와 블로그 서비스를 통합한 웹 애플리케이션**입니다. 

**주요 특징:**
- 기초적인 CRUD부터 페이징, 검색, Spring Security를 활용한 인증/인가 시스템까지 웹 개발의 필수 기능 구현
- MySQL 데이터베이스 연동 및 JPA를 활용한 데이터 관리
- Thymeleaf 템플릿 엔진을 활용한 동적 웹 페이지 구성

---

## 주차별 실습 내용 요약

### 2-1주차: 스프링 부트 개발환경 완료
- Spring Boot 3.5.7 프로젝트 생성
- Maven 빌드 도구 설정
- 기본 프로젝트 구조 이해

### 2-2주차: 컨트롤러 입문
- `@Controller` 어노테이션 학습
- `@GetMapping`을 통한 URL 매핑
- `Model` 객체를 통한 데이터 전달
- Thymeleaf 템플릿 연동

### 3주차: 포트폴리오 템플릿 작성
- Bootstrap 기반 포트폴리오 템플릿 적용
- Thymeleaf 문법 실습 (`th:text`, `th:each` 등)
- JavaScript 함수 작성 및 이벤트 처리
- **커스터마이징**: 프로필 이미지, 자기소개, 기술 경험 섹션을 본인 정보로 수정

### 4주차: MySQL 연동 및 작성
- MySQL 데이터베이스 연동 설정
- JPA/Hibernate 설정
- `@Entity`, `@Repository` 어노테이션 학습
- 데이터베이스 테이블 생성 및 데이터 조회

### 5주차: 블로그 게시판 조회, 글쓰기 구현
- 게시판 기본 CRUD 기능 구현
- `@PostMapping`을 통한 글 작성 처리
- `redirect:`를 활용한 페이지 리다이렉트
- 전역 예외 처리기 구현 (`@ControllerAdvice`)

### 6주차: 블로그 게시판 수정, 삭제 구현
- `@PutMapping`, `@DeleteMapping` 학습
- HiddenHttpMethodFilter 설정
- 게시글 수정/삭제 기능 구현

### 7주차: 블로그 게시판 글 수정
- JPA Dirty Checking 활용
- 게시글 수정 시 기존 필드 유지 로직 구현
- `Board` 엔티티를 활용한 게시판 시스템 구축

### 9주차: 게시판 – 검색, 페이징
- Spring Data JPA `Pageable` 인터페이스 활용
- 페이징 처리 구현 (페이지당 5개 게시글)
- 제목 기반 검색 기능 (`Containing` 메서드)
- 페이지 기준 순차 글번호 표시 로직 구현

### 10주차: 로그인과 로그아웃
- Spring Security 의존성 추가
- `PasswordEncoder`를 활용한 비밀번호 암호화
- `@Valid` 어노테이션을 통한 입력값 검증
- 회원가입/로그인/로그아웃 기능 구현

### 11주차: 게시판 수정하기(작성자, 권한)
- `HttpSession`을 활용한 세션 관리
- 로그인 사용자 정보를 통한 접근 제어
- 작성자 본인만 수정/삭제 가능하도록 권한 체크
- 서버에서 작성자 및 기본값 자동 설정

### 12주차: 세션처리와 파일 업로드
- 다중 사용자 세션 처리 (2명 이상 동시 로그인 가능)
- 파일 업로드 기능 구현
- 동일 파일명 처리 (타임스탬프 + UUID 추가)
- 파일 업로드 에러 페이지 구현

---

## 연습문제 해결 및 구현 내용

### 2주차 연습문제: URL 맵핑과 컨트롤러 추가하기

**문제**: `DemoController.java`에 `/hello2` 맵핑을 추가하고, Model을 사용해 5개의 데이터(data1~data5)를 View로 전달

**해결 방법**:
- `DemoController.java`에 `@GetMapping("/hello2")` 메서드 추가
- `model.addAttribute()`를 통해 5개의 데이터 전달
- `hello2.html`에서 `th:text` 속성을 이용해 데이터 출력

**구현 파일**:
- `controller/DemoController.java` (27-35줄)
- `templates/hello2.html`

```java
// DemoController.java
@GetMapping("/hello2")
public String hello2(Model model) {
  model.addAttribute("data1", " 홍길동님.");
  model.addAttribute("data2", " 방갑습니다.");
  model.addAttribute("data3", " 오늘.");
  model.addAttribute("data4", " 날씨는.");
  model.addAttribute("data5", " 매우 좋습니다.");
  return "hello2";
}
```

---

### 3주차 연습문제: 상세 페이지 수정하기 (창 닫기 기능)

**문제**: `about_detailed.html`에 '되돌아가기' 버튼과 '창닫기' 버튼 추가

**해결 방법**:
- `DemoController.java`에 `/index` 맵핑 추가
- `static/js/main.js`에 `confirmClose()` 함수 추가
- `about_detailed.html`에 버튼 및 이벤트 핸들러 추가

**구현 파일**:
- `controller/DemoController.java` (37-40줄)
- `static/js/main.js` (127-131줄)
- `templates/about_detailed.html`

```javascript
// main.js
function confirmClose() {
    if (confirm("정말 창을 닫으시겠습니까?")) {
        window.close();
    }
}
```

---

### 4주차 연습문제: 데이터베이스 연동 설정

**문제**: `testdb` 테이블에 사용자 데이터를 INSERT하고, Thymeleaf를 통해 리스트 출력

**해결 방법**:
- VS Code MySQL 확장을 사용하여 테이블에 데이터 INSERT
- `TestService.java`에 `findAllUsers()` 메서드 추가
- `DemoController.java`의 `/testdb` 맵핑에서 서비스 호출
- `testdb.html`에서 `th:each` 반복문으로 데이터 출력

**구현 파일**:
- `model/service/TestService.java` (23-25줄)
- `controller/DemoController.java` (58-65줄)
- `templates/testdb.html`

---

### 5주차 연습문제: 페이지 리다이렉트

**문제**: 글쓰기 처리 후 목록 페이지로 자동 이동

**해결 방법**:
- `BlogController.java`에 `@PostMapping("/api/articles")` 메서드 추가
- `blogService.save()` 호출 후 `return "redirect:/article_list"` 반환
- `BlogRestController.java`의 동일 경로 메서드 주석 처리하여 충돌 방지

**구현 파일**:
- `controller/BlogController.java` (183-190줄)

---

### 5주차 추가 연습문제: 예외처리

**문제**: URL 경로의 ID 파라미터 타입 불일치 시 커스텀 에러 페이지 표시

**해결 방법**:
- `exception` 패키지 생성 및 `GlobalExceptionHandler.java` 파일 추가
- `@ControllerAdvice` 어노테이션으로 전역 예외 처리기 등록
- `@ExceptionHandler(MethodArgumentTypeMismatchException.class)`로 특정 예외 처리
- `error_page/error_type_mismatch.html` 에러 페이지 생성

**구현 파일**:
- `exception/GlobalExceptionHandler.java`
- `templates/error_page/error_type_mismatch.html`

---

### 7주차 연습문제: 블로그 게시판 글 수정

**문제**: `Board` 엔티티를 사용하여 게시글 수정 기능 구현 (기존 필드 유지)

**해결 방법**:
- `BlogService.java`의 `update` 메서드를 `Board` 엔티티 사용하도록 수정
- 기존 게시글의 `user`, `newdate`, `count`, `likec` 값을 유지하면서 제목과 내용만 수정
- `BlogController.java`에 `/board_edit/{id}` GET, `/api/board_edit/{id}` PUT 맵핑 추가
- `board_edit.html` 템플릿 생성

**구현 파일**:
- `model/service/BlogService.java` (57-75줄)
- `controller/BlogController.java` (102-113줄, 165-169줄)
- `templates/board_edit.html`

```java
// BlogService.java
public void update(Long id, AddArticleRequest request) {
  Optional<Board> optionalBoard = blogRepository2.findById(id);
  optionalBoard.ifPresent(board -> {
    board.update(
      request.getTitle(), 
      request.getContent(), 
      board.getUser(), 
      board.getNewdate(), 
      board.getCount(), 
      board.getLikec()
    );
    blogRepository2.save(board);
  });
}
```

---

### 9주차 연습문제: 게시판 – 검색, 페이징

**문제 1**: 게시판 페이지 글 번호를 DB의 id 대신 페이지 기준 순차 번호로 변경

**해결 방법**:
- `BlogController.java`의 `/board_list`에서 `startNum` 계산: `int startNum = (page * pageSize) + 1;`
- `board_list.html`에서 `th:each`의 `iterStat.index`를 활용하여 순차 번호 출력

**구현 파일**:
- `controller/BlogController.java` (74-75줄)
- `templates/board_list.html` (42-43줄)

**문제 2**: 게시판 삭제 기능 구현

**해결 방법**:
- `BlogService.java`에 `deleteBoard(Long id)` 메서드 추가
- `BlogController.java`에 `/api/board_delete/{id}` DELETE 맵핑 추가

**구현 파일**:
- `model/service/BlogService.java` (81-83줄)
- `controller/BlogController.java` (177-181줄)

---

### 10주차 연습문제: 입력값 필터링

**문제**: 회원가입 시 입력값 검증 구현

**해결 방법**:
- `pom.xml`에 `spring-boot-starter-validation` 의존성 추가
- `AddMemberRequest.java`에 검증 어노테이션 추가:
  - 이름: `@NotBlank`, `@Pattern` (특수문자 제외)
  - 이메일: `@NotBlank`, `@Email`
  - 비밀번호: `@NotBlank`, `@Pattern` (8자 이상, 대소문자 및 숫자 포함)
  - 나이: `@NotBlank`, `@Pattern` (19세 이상 90세 이하)
- `MemberController.java`의 `addmembers` 메서드에 `@Valid` 어노테이션 추가
- `memberService.java` 클래스에 `@Validated` 어노테이션 추가

**구현 파일**:
- `pom.xml` (64-66줄)
- `model/service/AddMemberRequest.java` (14-28줄)
- `controller/MemberController.java` (33줄)
- `model/service/memberService.java` (16줄)

```java
// AddMemberRequest.java
@NotBlank(message = "이름은 비울 수 없습니다.")
@Pattern(regexp = "^[A-Za-z가-힣0-9]+$", message = "이름에는 특수문자를 사용할 수 없습니다.")
private String name;

@NotBlank(message = "이메일은 비울 수 없습니다.")
@Email(message = "이메일 형식이 올바르지 않습니다.")
private String email;

@NotBlank(message = "비밀번호는 비울 수 없습니다.")
@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "비밀번호는 대소문자, 숫자를 포함해 8자 이상이어야 합니다.")
private String password;
```

---

### 11주차 연습문제: 게시판 수정하기(작성자, 권한)

**문제**: 작성자 본인에게만 수정/삭제 버튼 노출

**해결 방법**:
- 글 저장 시 작성자를 로그인 사용자(email)로 설정하고 기본값(newdate, count, likec)을 서버에서 세팅
- 글쓰기 화면에서 작성자를 로그인 이메일로 표시(읽기 전용)
- 글 상세 보기에서 로그인 사용자와 작성자가 일치할 때만 수정/삭제 버튼 노출 (`th:if` 사용)

**구현 파일**:
- `controller/BlogController.java` (131-140줄, 142-156줄, 87-100줄)
- `templates/board_write.html` (18-20줄)
- `templates/board_view.html` (52-65줄)

```java
// BlogController.java (글쓰기 저장)
@PostMapping("/api/boards")
public String addboards(@ModelAttribute AddArticleRequest request, HttpSession session) {
  String email = (String) session.getAttribute("email");
  if (email == null) return "redirect:/member_login";
  request.setUser(email);
  request.setNewdate(LocalDate.now().toString());
  request.setCount("0");
  request.setLikec("0");
  blogService.save(request);
  return "redirect:/board_list";
}
```

```html
<!-- board_view.html -->
<td th:if="${email == board.user}">
  <a class="btn btn-warning" th:href="@{/board_edit/{id}(id=${board.id})}">수정</a>
  <form th:action="@{/api/board_delete/{id}(id=${board.id})}" method="post" style="display:inline;">
    <input type="hidden" name="_method" value="delete">
    <button type="submit" class="btn btn-danger">삭제</button>
  </form>
</td>
<td th:if="${email != board.user}">
  <span class="text-muted">본인 글만 수정/삭제할 수 있습니다.</span>
</td>
```

---

### 12주차 연습문제: 세션처리와 파일 업로드

**문제 1**: 2명 이상 로그인 가능하도록 세션 처리 개선

**해결 방법**:
- 로그인 시 기존 세션 무효화 로직 제거
- 사용자마다 고유 세션 생성 (`UUID.randomUUID()`)
- 로그아웃 시 현재 사용자의 세션과 쿠키만 삭제

**구현 파일**:
- `controller/MemberController.java` (43-60줄, 62-79줄)

```java
// MemberController.java (로그인)
@PostMapping("/api/login_check")
public String checkMembers(...) {
    // 사용자마다 다른 이름 세션 생성 (2명 이상 로그인 가능)
    HttpSession session = request2.getSession(true); // 기존 세션 무효화하지 않음
    String sessionId = UUID.randomUUID().toString();
    session.setAttribute("userId", sessionId);
    session.setAttribute("email", email);
    ...
}
```

**문제 2**: 파일 업로드 시 동일 파일명 처리 및 에러 페이지 구현

**해결 방법**:
- 동일 파일명이 존재하면 타임스탬프와 UUID를 추가하여 다른 이름으로 저장
- 파일 업로드 에러 발생 시 전용 에러 페이지로 리다이렉트

**구현 파일**:
- `controller/FileController.java` (24-75줄)
- `templates/error_page/file_upload_error.html`

```java
// FileController.java
if (Files.exists(filePath)) {
    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
    String uuid = UUID.randomUUID().toString().substring(0, 8);
    String newFileName = sanitizedEmail + "_" + timestamp + "_" + uuid + ".txt";
    filePath = uploadPath.resolve(newFileName);
}
```
---

## 실제 결과 스크린샷

### 1. 포트폴리오 메인 페이지
![포트폴리오 메인 페이지](screenshots/01_portfolio_main.png)
*포트폴리오 메인 페이지 - 헤더, 자기소개, 기술 경험 섹션*

### 2. 로그인 페이지
![로그인 페이지](screenshots/02_login.png)
*로그인 폼 - 이메일과 비밀번호 입력*

### 3. 회원가입 페이지
![회원가입 페이지](screenshots/03_join.png)
*회원가입 폼 - 이름, 이메일, 비밀번호, 나이, 전화번호, 주소 입력*

### 4. 게시판 목록
![게시판 목록](screenshots/04_board_list.png)
*게시판 목록 - 검색창, 페이징, 로그인 사용자 표시*

### 5. 게시판 상세 보기 (본인 글)
![게시판 상세 보기 - 본인 글](screenshots/05_board_view_own.png)
*게시글 상세 보기 - 본인 글일 경우 수정/삭제 버튼 표시*

### 6. 게시판 상세 보기 (타인 글)
![게시판 상세 보기 - 타인 글](screenshots/06_board_view_other.png)
*게시글 상세 보기 - 타인 글일 경우 권한 안내 메시지 표시*

### 7. 게시글 작성
![게시글 작성](screenshots/07_board_write.png)
*게시글 작성 폼 - 작성자 자동 입력 (읽기 전용), 제목/내용 입력*

### 8. 파일 업로드 완료
![파일 업로드 완료](screenshots/08_upload_end.png)
*파일 업로드 성공 메시지*

---

## 기술 스택

- **Backend**: Spring Boot 3.5.7, Java 21
- **Database**: MySQL 8.0
- **ORM**: Spring Data JPA / Hibernate
- **Template Engine**: Thymeleaf
- **Frontend**: Bootstrap 4, JavaScript, jQuery
- **Security**: Spring Security (비밀번호 암호화)
- **Validation**: Bean Validation (Jakarta Validation)
- **Build Tool**: Maven
- **기타**: Lombok, Spring DevTools

---

## 주요 기능 상세

### 1. 포트폴리오 기능
- **메인 페이지** (`index.html`): 개인 소개, 기술 경험, 프로젝트 포트폴리오
- **상세 페이지** (`about_detailed.html`): 상세 소개 및 되돌아가기/창닫기 기능
- **연락하기 폼**: 이메일 내용을 파일로 업로드

### 2. 회원 관리 기능
- **회원가입**: 입력값 검증 (이름, 이메일, 비밀번호, 나이)
- **로그인**: 이메일/비밀번호 인증, 세션 관리
- **로그아웃**: 현재 사용자 세션 및 쿠키 삭제
- **비밀번호 암호화**: BCryptPasswordEncoder 사용

### 3. 게시판 기능
- **게시글 목록**: 페이징 (페이지당 5개), 검색 기능
- **게시글 작성**: 로그인 사용자만 가능, 작성자 자동 설정
- **게시글 조회**: 상세 내용 확인
- **게시글 수정**: 작성자 본인만 가능, 기존 필드 유지
- **게시글 삭제**: 작성자 본인만 가능
- **권한 관리**: 로그인 사용자와 작성자 일치 여부로 수정/삭제 버튼 표시

### 4. 파일 업로드 기능
- **이메일 내용 저장**: 연락하기 폼의 내용을 텍스트 파일로 저장
- **중복 파일명 처리**: 타임스탬프 + UUID를 추가하여 고유 파일명 생성
- **에러 처리**: 파일 업로드 실패 시 전용 에러 페이지 표시

### 5. 예외 처리
- **전역 예외 처리**: `@ControllerAdvice`를 통한 예외 처리
- **타입 불일치 예외**: 잘못된 파라미터 타입 접근 시 커스텀 에러 페이지

---

## 데이터베이스 구조

### 테이블 정보

#### `board` 테이블
- `id` (Long, PK, AUTO_INCREMENT): 게시글 고유 번호
- `title` (String, NOT NULL): 게시글 제목
- `content` (String, NOT NULL): 게시글 내용
- `user` (String, NOT NULL): 작성자 (이메일)
- `newdate` (String, NOT NULL): 작성일
- `count` (String, NOT NULL): 조회수
- `likec` (String, NOT NULL): 좋아요 수

#### `member` 테이블
- `id` (Long, PK, AUTO_INCREMENT): 회원 고유 번호
- `name` (String, NOT NULL): 이름
- `email` (String, UNIQUE, NOT NULL): 이메일 (중복 불가)
- `password` (String, NOT NULL): 암호화된 비밀번호
- `age` (String, NOT NULL): 나이
- `mobile` (String, NOT NULL): 전화번호
- `address` (String, NOT NULL): 주소

#### `article` 테이블
- `id` (Long, PK, AUTO_INCREMENT): 게시글 고유 번호
- `title` (String, NOT NULL): 게시글 제목
- `content` (String, NOT NULL): 게시글 내용

#### `testdb` 테이블
- `id` (Long, PK, AUTO_INCREMENT): 사용자 고유 번호
- `name` (String): 이름

---

## 주요 구현 파일 및 메서드

### 컨트롤러

#### `BlogController.java`
- `GET /board_list`: 게시판 목록 조회 (페이징, 검색)
- `GET /board_view/{id}`: 게시글 상세 조회
- `GET /board_write`: 글쓰기 화면
- `GET /board_edit/{id}`: 글 수정 화면
- `POST /api/boards`: 게시글 저장
- `PUT /api/board_edit/{id}`: 게시글 수정
- `DELETE /api/board_delete/{id}`: 게시글 삭제

#### `MemberController.java`
- `GET /join_new`: 회원가입 페이지
- `POST /api/members`: 회원가입 처리
- `GET /member_login`: 로그인 페이지
- `POST /api/login_check`: 로그인 처리
- `GET /api/logout`: 로그아웃 처리

#### `FileController.java`
- `POST /upload-email`: 파일 업로드 처리
- `GET /error_page/file_upload_error`: 파일 업로드 에러 페이지

### 서비스

#### `BlogService.java`
- `findAll(Pageable)`: 게시글 목록 조회 (페이징)
- `searchByKeyword(String, Pageable)`: 키워드 검색
- `findById(Long)`: 게시글 단건 조회
- `save(AddArticleRequest)`: 게시글 저장
- `update(Long, AddArticleRequest)`: 게시글 수정 (기존 필드 유지)
- `deleteBoard(Long)`: 게시글 삭제

#### `memberService.java`
- `saveMember(AddMemberRequest)`: 회원가입 (비밀번호 암호화, 이메일 중복 체크)
- `loginCheck(String, String)`: 로그인 검증 (이메일, 비밀번호 확인)

---

**작성일**: 2025년 12월  
**작성자**: 20221022 정우영
