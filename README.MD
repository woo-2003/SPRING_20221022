1. 전체 실습과정 완료 • 기본 기능 동작.
• 2-1주차[스프링 부트 개발환경 완료]
• 2-2주차[컨트롤러 입문]
• 3주차[포트폴리오템플릿 작성]

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
  
  창닫기 기능 추가
  
  <a href="#" onclick="confirmClose()" class="btn btn-primary py-3 px-5">창닫기</a>

3. 내용 수정 • 텍스트,디자인 등

4. 소스 코드 정리 • 파일명, 들여쓰기, 주석 표시
HTML (templates):

index.html: 메인 포트폴리오 페이지 (3주차 수정)

about_detailed.html: '상세 소개' 전용 페이지 (3주차 추가)

hello.html, hello2.html: (2주차 연습문제용 파일)

Java (Controller):


DemoApplication.java: @SpringBootApplication 어노테이션을 포함한 메인 시작 파일 


DemoController.java: 모든 URL 맵핑(@GetMapping)을 관리 (/hello, /hello2, /about_detailed) 


Static Resources (static):


css/, js/, img/, lib/: 템플릿의 정적 자원(스타일, 이미지, 폰트 등)을 분리하여 관리 


js/main.js: 템플릿 기본 기능 및 confirmClose() 함수가 추가된 스크립트 파일 

Configuration (resources):


application.properties: 정적 리소스 경로(spring.web.resources.static-locations) 설정을 추가 


pom.xml: DB 에러 방지를 위해 spring-boot-starter-data-jpa와 mysql-connector-j를 주석 처리