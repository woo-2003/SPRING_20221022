1. 전체 실습과정 완료 • 기본 기능 동작.
• 2-1주차[스프링 부트 개발환경 완료]
• 2-2주차[컨트롤러 입문]

2. 연습문제(모두 완료)
• 2주차[URL 맵핑과 컨트롤러 추가하기]
   컨트롤러
   [(@GetMapping("/hello2") // 전송 방식 GET
  public String hello2(Model model) {
    model.addAttribute("data1", " 홍길동님."); // model 설정
    model.addAttribute("data2", " 방갑습니다.");
    model.addAttribute("data3", " 오늘.");
    model.addAttribute("data4", " 날씨는.");
    model.addAttribute("data5", " 매우 좋습니다.");
    return "hello2"; // hello.html 연결
  })]
  페이지
  [선언 - < xmlns:th="http://www.thymeleaf.org">  
  바디 - 
  th:text="${data1}">
  th:text="${data2}">
  th:text="${data3}">
  th:text="${data4}">
  th:text="${data5}">
  a href="/hello2">홈페이지 메인
  ]


3. 내용 수정 • 텍스트,디자인 등

4. 소스 코드 정리 • 파일명, 들여쓰기, 주석 표시