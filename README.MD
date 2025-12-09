**자바웹프(2)_중간점검_20221022_정우영**
## 1. 전체 실습과정 완료
- 기본 기능 동작.
- 2-1주차[스프링 부트 개발환경 완료]
- 2-2주차[컨트롤러 입문]
- 3주차[포트폴리오템플릿 작성]
- 4주차: [MYSQl 연동 및 작성]
- 5주차: [블로그 게시판 조회, 글쓰기 구현]
- 6주차: [블로그 게시판 수정 ,삭제 구현]
- 7주차: [블로그 게시판 글 수정]
- 9주차: [게시판 – 검색, 페이징]
- 10주차: [로그인과 로그아웃]
- 11주차: [게시판 수정하기(작성자, 권한)]
- 12주차: [세션처리와 파일 업로드]

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

- 7주차: [블로그 게시판 글 수정]
  - BlogService.java의 update 메서드를 Board 엔티티 사용하도록 수정.
  - 기존 게시글의 user, newdate, count, likec 값을 유지하면서 제목과 내용만 수정하도록 구현.

```java
// BlogService.java
public void update(Long id, AddArticleRequest request) {
  Optional<Board> optionalBoard = blogRepository2.findById(id); // 단일 글 조회
  optionalBoard.ifPresent(board -> { // 값이 있으면
    // 기존 게시글의 user, newdate, count, likec 값을 유지하면서 제목과 내용만 수정
    board.update(
      request.getTitle(), 
      request.getContent(), 
      board.getUser(), 
      board.getNewdate(), 
      board.getCount(), 
      board.getLikec()
    ); // 값을 수정
    blogRepository2.save(board); // Board 객체에 저장
  });
}
```

  - BlogController.java에 /board_edit/{id} GET 맵핑 추가하여 수정 화면 표시.

```java
@GetMapping("/board_edit/{id}") // 게시판 글 수정 화면 맵핑
public String board_edit(Model model, @PathVariable Long id) {
  Optional<Board> list = blogService.findById(id); // 선택한 게시판 글
  if (list.isPresent()) {
    model.addAttribute("board", list.get()); // 존재하면 Board 객체를 모델에 추가
  } else {
    return "error_page/article_error"; // 오류 처리 페이지로 연결
  }
  return "board_edit"; // .HTML 연결
}
```

  - BlogController.java에 /api/board_edit/{id} PUT 맵핑 추가하여 수정 처리 후 board_list로 리다이렉트.

```java
@PutMapping("/api/board_edit/{id}")
public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
  blogService.update(id, request);
  return "redirect:/board_list"; // 글 수정 이후 board_list.html 연결
}
```

  - board_edit.html 템플릿 생성. 현재 게시글의 제목과 내용을 폼에 미리 채워넣어 수정 가능하도록 구현.

- 9주차: [게시판 – 검색, 페이징]
  - 게시판 페이지 글 번호 표시 개선: DB의 id 대신 페이지 기준 순차 번호로 변경.

```java
// BlogController.java
@GetMapping("/board_list")
public String board_list(...) {
  int pageSize = 5; // 한 페이지의 게시글 수
  // 페이지 기준 시작 번호 계산
  int startNum = (page * pageSize) + 1;
  model.addAttribute("startNum", startNum); // 시작 번호
  ...
}
```

  - board_list.html에서 "ID" 컬럼을 "글번호"로 변경하고 순차 번호 출력.

```html
<!-- board_list.html -->
<thead>
  <tr>
    <th>글번호</th>
    ...
  </tr>
</thead>
<tbody>
  <tr th:each="board, iterStat : ${boards.content}">
    <td th:text="${startNum + iterStat.index}"></td>
    ...
  </tr>
</tbody>
```

  - BlogService.java에 deleteBoard(Long id) 메서드 추가.

```java
// BlogService.java
public void deleteBoard(Long id) {
  blogRepository2.deleteById(id);
}
```

  - BlogController.java에 /api/board_delete/{id} DELETE 맵핑 추가하여 삭제 처리 후 board_list로 리다이렉트.

```java
@DeleteMapping("/api/board_delete/{id}")
public String deleteBoard(@PathVariable Long id) {
  blogService.deleteBoard(id);
  return "redirect:/board_list";
}
```

- 10주차: [입력값 필터링]
  - pom.xml에 spring-boot-starter-validation 의존성 추가.

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

  - AddMemberRequest.java에 검증 어노테이션 추가.
    - 이름: @NotBlank, @Pattern으로 특수문자 제외 검증.
    - 이메일: @NotBlank, @Email로 이메일 형식 검증.
    - 비밀번호: @NotBlank, @Pattern으로 8자 이상, 대소문자 및 숫자 포함 검증.
    - 나이: @NotBlank, @Pattern으로 19세 이상 90세 이하 검증.
    - 모바일, 주소: 공백 허용(검증 없음).

```java
// AddMemberRequest.java
@NotBlank(message = "이름은 필수입니다.")
@Pattern(regexp = "^[A-Za-z가-힣0-9]+$", message = "이름에는 특수문자를 사용할 수 없습니다.")
private String name;

@NotBlank(message = "이메일은 필수입니다.")
@Email(message = "올바른 이메일 형식이 아닙니다.")
private String email;

@NotBlank(message = "비밀번호는 필수입니다.")
@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "비밀번호는 8자 이상, 대소문자 및 숫자를 포함해야 합니다.")
private String password;

@NotBlank(message = "나이는 필수입니다.")
@Pattern(regexp = "^(19|[2-8]\\d|90)$", message = "나이는 19세 이상 90세 이하여야 합니다.")
private String age;
```

  - MemberController.java의 addmembers 메서드에 @Valid 어노테이션 추가.

```java
@PostMapping("/api/members") // 회원 가입 저장
public String addmembers(@Valid @ModelAttribute AddMemberRequest request) {
    memberService.saveMember(request);
    return "join_end"; // .HTML 연결
}
```

  - memberService.java 클래스에 @Validated 어노테이션 추가하여 서비스 계층에서도 검증 활성화.

```java
@Service
@Validated // 서비스 계층에서 검증 활성화
@Transactional
@RequiredArgsConstructor
public class memberService {
    ...
}
```

- 11주차: [게시판 수정하기(작성자, 권한)]
  - 글 저장 시 작성자를 로그인 사용자(email)로 설정하고 기본값(newdate, count, likec)을 서버에서 세팅.

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

  - 글쓰기 화면에서 작성자를 로그인 이메일로 표시(읽기 전용).

```html
<!-- board_write.html -->
<input type="text" class="form-control" id="author" th:value="${email}" readonly>
```

  - 글 상세 보기에서 로그인 사용자와 작성자가 일치할 때만 수정/삭제 버튼 노출.

```html
<!-- board_view.html -->
<tr th:each="board : ${boards}">
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
</tr>
```

- 12주차: [세션처리와 파일 업로드]
  - 세션 처리 개선: 2명 이상 로그인 가능하도록 수정. 사용자마다 고유 세션 생성, 로그아웃 시 현재 사용자 세션만 삭제.

```java
// MemberController.java (로그인)
@PostMapping("/api/login_check")
public String checkMembers(...) {
    // 사용자마다 다른 이름 세션 생성 (2명 이상 로그인 가능)
    HttpSession session = request2.getSession(true); // 새로운 세션 생성 (기존 세션 무효화하지 않음)
    String sessionId = UUID.randomUUID().toString(); // 임의의 고유 ID로 세션 생성
    session.setAttribute("userId", sessionId);
    session.setAttribute("email", email);
    ...
}
```

```java
// MemberController.java (로그아웃)
@GetMapping("/api/logout")
public String member_logout(...) {
    HttpSession session = request2.getSession(false);
    if (session != null) {
        // 현재 사용자의 세션과 쿠키만 삭제
        session.invalidate();
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
    return "login";
}
```

  - 파일 업로드 개선: 동일 파일명 처리(타임스탬프와 UUID 추가), 파일 업로드 에러 페이지 처리.

```java
// FileController.java
@PostMapping("/upload-email")
public String uploadEmail(...) {
    String baseFileName = sanitizedEmail + ".txt";
    Path filePath = uploadPath.resolve(baseFileName);
    
    // 동일 파일명이 존재하면 타임스탬프와 UUID를 추가하여 다른 이름으로 저장
    if (Files.exists(filePath)) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        String newFileName = sanitizedEmail + "_" + timestamp + "_" + uuid + ".txt";
        filePath = uploadPath.resolve(newFileName);
    }
    ...
    } catch (IOException e) {
        redirectAttributes.addFlashAttribute("error", "파일 업로드 중 오류가 발생했습니다: " + e.getMessage());
        return "redirect:/error_page/file_upload_error"; // 파일 업로드 에러 페이지로 리다이렉트
    }
}
```

  - 파일 업로드 에러 페이지 생성: error_page/file_upload_error.html 생성.

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
  - pom.xml: Spring Boot 3.5.7, Java 21, Web/Thymeleaf/JPA/Actuator, Lombok, Devtools, MySQL Connector, Validation 의존성 및 플러그인 설정(10주차 수정).
  - mvnw, mvnw.cmd: Maven Wrapper 실행 스크립트.

- Java (src/main/java)
  - DemoApplication.java: 애플리케이션 진입점. `SpringApplication.run(...)`으로 부트스트랩.
  - controller/BlogController.java: 게시판 화면 컨트롤러.
    - `GET /article_list`: 게시글 목록 조회 후 `article_list.html` 렌더.
    - `GET /article_edit/{id}`: 단건 조회 후 수정 화면 렌더(없으면 오류 페이지로 분기).
    - `POST /api/articles`: 글 저장 후 목록으로 리다이렉트.
    - `PUT /api/article_edit/{id}`: 글 수정 처리 후 목록으로 리다이렉트.
    - `DELETE /api/article_delete/{id}`: 글 삭제 처리 후 목록으로 리다이렉트.
    - `GET /board_list`: Board 게시글 목록 조회(페이징, 검색 지원) 후 `board_list.html` 렌더. 페이지 기준 시작 번호(startNum) 계산하여 전달(9주차 수정).
    - `GET /board_view/{id}`: Board 게시글 상세 조회 후 `board_view.html` 렌더. 로그인 사용자 이메일을 함께 전달(11주차 수정).
    - `GET /board_write`: 글쓰기 화면 렌더. 로그인 사용자만 접근, 이메일을 모델로 전달(11주차 수정).
    - `GET /board_edit/{id}`: Board 게시글 수정 화면 렌더(7주차 추가).
    - `POST /api/boards`: Board 글 저장 후 목록으로 리다이렉트. 작성자를 로그인 이메일로 설정하고 기본값(newdate, count, likec) 서버에서 세팅(11주차 수정).
    - `PUT /api/board_edit/{id}`: Board 글 수정 처리 후 목록으로 리다이렉트(7주차 추가).
    - `DELETE /api/board_delete/{id}`: Board 글 삭제 처리 후 목록으로 리다이렉트(9주차 추가).
  - controller/BlogRestController.java: REST 관련 보조 컨트롤러.
    - `GET /favicon.ico`: 빈 처리(불필요한 에러 방지). 주석 처리된 REST 예제 포함.
  - controller/MemberController.java: 회원 관련 컨트롤러.
    - `GET /join_new`: 회원 가입 페이지 렌더.
    - `POST /api/members`: 회원 가입 처리. @Valid로 입력값 검증(10주차 수정).
    - `GET /member_login`: 로그인 페이지 렌더.
    - `POST /api/login_check`: 로그인 처리(이메일, 비밀번호 확인). 사용자마다 고유 세션 생성, 2명 이상 로그인 가능(12주차 수정).
    - `GET /api/logout`: 로그아웃 처리. 현재 사용자의 세션과 쿠키만 삭제(12주차 수정).
  - controller/FileController.java: 파일 업로드 컨트롤러(12주차 추가).
    - `POST /upload-email`: 이메일 내용을 파일로 업로드. 동일 파일명 처리(타임스탬프+UUID 추가), 에러 페이지 처리 포함.
    - `GET /error_page/file_upload_error`: 파일 업로드 에러 페이지 렌더.
  - controller/DemoController.java: 실습용 화면 컨트롤러.
    - `GET /hello`, `/hello2`: 모델 데이터 전달 및 템플릿 렌더.
    - `GET /about_detailed`, `/index`: 포트폴리오/메인 페이지 연결.
    - `GET /test1`: Thymeleaf 문법 실습 페이지 연결.
    - `GET /testdb`: DB 사용자 목록 조회(Model에 `users`) 후 `testdb.html` 렌더.
  - exception/GlobalExceptionHandler.java: 전역 예외 처리.
    - `MethodArgumentTypeMismatchException` 발생 시 `error_page/error_type_mismatch` 반환.
  - model/domain/Article.java: 게시글 JPA 엔티티(id, title, content). `@Builder`, `update(title, content)` 제공.
  - model/domain/Board.java: 게시글 JPA 엔티티(id, title, content, user, newdate, count, likec). `@Builder`, `update(...)` 제공.
  - model/domain/Member.java: 회원 JPA 엔티티(id, name, email, password, age, mobile, address). `@Builder` 제공. email은 unique 제약.
  - model/domain/TestDB.java: 예제 JPA 엔티티(id, name).
  - model/repository/BlogRepository.java: `Article`용 `JpaRepository<Article, Long>`.
  - model/repository/BoardRepository.java: `Board`용 `JpaRepository<Board, Long>` + `findByTitleContainingIgnoreCase` (페이징 검색 지원).
  - model/repository/MemberRepository.java: `Member`용 `JpaRepository<Member, Long>` + `findByEmail(String)`.
  - model/repository/TestRepository.java: `TestDB`용 `JpaRepository<TestDB, Long>` + `findByName(String)`.
  - model/service/AddArticleRequest.java: 게시글 생성/수정 DTO. `toEntity()`로 `Board` 빌드. title, content 외 user, newdate, count, likec 필드 포함.
  - model/service/AddMemberRequest.java: 회원 가입 DTO. `toEntity()`로 `Member` 빌드. @NotBlank, @Email, @Pattern 등 검증 어노테이션 적용(10주차 수정).
  - model/service/BlogService.java: 게시판 비즈니스 로직. `findAll`, `save`, `findById`, `update`, `delete`, `deleteBoard` 제공. `update` 메서드는 Board 엔티티 사용, 기존 필드(user, newdate, count, likec) 유지하며 제목/내용만 수정(7주차 수정). `deleteBoard` 메서드 추가(9주차 추가).
  - model/service/memberService.java: 회원 비즈니스 로직. @Validated 어노테이션으로 서비스 계층 검증 활성화(10주차 수정). `saveMember`, `loginCheck` 제공. 비밀번호 암호화, 이메일 중복 검사 포함.
  - model/service/TestService.java: 사용자 조회 비즈니스. `findByName`, `findAllUsers` 제공.

- Resources (src/main/resources)
  - application.properties: 앱 이름, 정적 리소스 경로, MySQL 접속 정보, JPA 설정(ddl-auto=update, show-sql), HiddenHttpMethodFilter 활성화.
  - templates/article_list.html: 게시글 목록 테이블 + 글 추가 폼. 수정/삭제 UI와 폼 전송 포함.
  - templates/article_edit.html: 게시글 수정 폼 화면.
  - templates/board_list.html: Board 게시글 목록 테이블(페이징, 검색 지원). 글쓰기 버튼 포함. "ID" 컬럼을 "글번호"로 변경하고 페이지 기준 순차 번호 출력(9주차 수정).
  - templates/board_view.html: Board 게시글 상세 보기. 작성자와 로그인 이메일이 일치할 때만 수정/삭제 버튼 표시(11주차 수정).
  - templates/board_write.html: Board 글쓰기 폼 화면. 로그인 사용자의 이메일을 작성자로 표시(11주차 수정).
  - templates/board_edit.html: Board 게시글 수정 폼 화면. 현재 제목/내용을 폼에 미리 채워넣음(7주차 추가).
  - templates/error_page/error_type_mismatch.html: 잘못된 파라미터 타입 접근 시 표시하는 에러 페이지.
  - templates/error_page/file_upload_error.html: 파일 업로드 오류 시 표시하는 에러 페이지(12주차 추가).
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
