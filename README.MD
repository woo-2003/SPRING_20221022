1. 전체 실습과정 완료 • 기본 기능 동작.
• 2-1주차[스프링 부트 개발환경 완료]
• 2-2주차[컨트롤러 입문]
• 3주차[포트폴리오템플릿 작성]
• 4주차: [MYSQl 연동 및 작성]
• 5주차: [블로그 게시판 글쓰기 , 수정 구현]

2. 연습문제(모두 완료)
• 2주차: URL 맵핑과 컨트롤러 추가하기
  DemoController.java에 /hello2 맵핑을 추가하고, Model을 사용해 5개의 데이터(data1~data5)를 View로 전달

  Java

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
  hello2.html에서 Thymeleaf의 th:text 속성을 이용해 컨트롤러가 보낸 5개의 데이터를 출력

  HTML

  <p th:text="${data1}"></p>
  <p th:text="${data2}"></p>
  <p th:text="${data3}"></p>
  <p th:text="${data4}"></p>
  <p th:text="${data5}"></p>
  <a href="index/">홈페이지 메인</a>


• 3주차: [상세 페이지 수정하기 (창 닫기 기능)] 

  about_detailed.html의 '되돌아가기' 버튼과 '창닫기' 버튼 추가, onclick="confirmClose()" 추가

  DemoController.java에 맵핑 추가

  @GetMapping("/index")
      public String index() {
      return "index";
      }

  되돌아가기 기능 추가

      <a class="btn btn-primary py-3 px-5" href="/index">되돌아가기</a>

  static/js/main.js 파일에 confirmClose() 함수 추가

  function confirmClose() {
      if (confirm("정말 창을 닫으시겠습니까?")) {
          window.close(); 
      }
  }
  
  창닫기 기능
  <a href="#" onclick="confirmClose()" class="btn btn-primary py-3 px-5">창닫기</a>

• 4주차: [데이터베이스 연동 설정] 
VS Code MySQL 확장을 사용하여 testdb 테이블에 사용자 2명(아저씨, 꾸러기) INSERT 완료


TestService.java에 findAllUsers() 메서드를 추가하여 testRepository.findAll() 호출


DemoController.java의 /testdb 맵핑을 수정하여 findAllUsers() 결과를 Model에 "users"라는 이름으로 추가


testdb.html을 수정하여 Thymeleaf th:each 반복문을 사용해 users 리스트의 모든 사용자 ID와 이름을 출력


• 5주차: [페이지 리다이렉트]

글쓰기 처리 방식을 @Controller 방식으로 변경.


BlogController.java에 @PostMapping("/api/articles") 메서드 추가 .

게시글 저장(blogService.save()) 후, "redirect:/article_list"를 반환하여 목록 페이지로 자동 이동(리다이렉트) 구현.

충돌 방지를 위해 BlogRestController.java의 @PostMapping("/api/articles") 메서드 주석 처리.



3. 내용 수정 • 텍스트,디자인 등
메뉴 수정: 네비게이션 바 메뉴 한글화 (3주차) . 
• 프로필 이미지 src="img/profile2.png(AI 본인 프로필사진) 으로 교체 (3주차). 
• 내소개(About)' 텍스트 수정 (3주차) . 
• 기술 & 경험(Skills & Expertise) 섹션 텍스트 한글 수정 (4주차) . 
• Thymeleaf 테스트 페이지 (thymeleaf_test1.html) 생성 및 th:utext, th:value, th:field, th:href, th:with 문법 실습 (4주차)
• 게시판 링크 추가: index.html 네비게이션 바 '게시판' 메뉴에 /article_list 링크 추가 (5주차) .

4. 소스 코드 정리 • 파일명, 들여쓰기, 주석 표시
HTML (templates):

index.html, about_detailed.html, hello.html, hello2.html, testdb.html, thymeleaf_test1.html

article_list.html: 블로그 게시판 목록 및 글쓰기 폼 페이지 (5주차 추가)

Java:

DemoApplication.java

controller/DemoController.java

controller/BlogController.java: 게시판 조회(/article_list) 및 글쓰기 후 리다이렉트(/api/articles POST) 맵핑 (5주차 추가/수정)

controller/BlogRestController.java: REST 방식 글쓰기(/api/articles POST) 맵핑 (5주차 추가, 연습문제 풀이 시 주석 처리)

domain/TestDB.java

domain/Article.java: 게시판 글 @Entity 클래스 (5주차 추가)

repository/TestRepository.java

repository/BlogRepository.java: 게시판 JpaRepository 인터페이스 (5주차 추가)

service/TestService.java

service/BlogService.java: 게시판 비즈니스 로직 (findAll, save) (5주차 추가)

service/AddArticleRequest.java: 게시글 추가용 DTO 클래스 (5주차 추가)

Static Resources (static):

css/, js/, img/, lib/

js/main.js (3주차 수정)

Configuration (resources):

application.properties (3, 4주차 수정)

pom.xml (4주차 수정)