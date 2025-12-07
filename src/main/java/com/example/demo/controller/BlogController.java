package com.example.demo.controller;
// import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;
import com.example.demo.model.service.AddArticleRequest;
// import com.example.demo.model.domain.TestDB;
import com.example.demo.model.service.BlogService; // 최상단 서비스 클래스 연동 추가

import jakarta.servlet.http.HttpSession;

@Controller // 컨트롤러 어노테이션 명시
public class BlogController{
  @Autowired
  BlogService blogService; // DemoController 클래스 아래 객체 생성

  // @GetMapping("/article_list")
  //   public String article_list() {
  //     return "article_list";
  //   }

    @GetMapping("/article_list") // 게시판 링크 지정
    public String article_list(Model model) {
      //List<Article> list = blogService.findAll(); // 게시판 리스트
      //model.addAttribute("articles", list); // 모델에 추가
      return "article_list"; // .HTML 연결
    }

    // @GetMapping("/board_list") // 새로운 게시판 링크 지정
    // public String board_list(Model model) {
    //   List<Board> list = blogService.findAll(); // 게시판 전체 리스트, 기존 Article에서 Board로 변경됨
    //   model.addAttribute("boards", list); // 모델에 추가
    //   return "board_list"; // .HTML 연결
    // }

    @GetMapping("/board_list") // 새로운 게시판 링크 지정
    public String board_list(
      Model model, 
      @RequestParam(defaultValue = "0") int page, 
      @RequestParam(defaultValue = "") String keyword, HttpSession session){ // 세션 객체 전달
      String userId = (String) session.getAttribute("userId"); // 세션 아이디 존재 확인
      String email = (String) session.getAttribute("email"); // 세션에서 이메일 확인

      if (userId == null) {
        return "redirect:/member_login"; // 로그인 페이지로 리다이렉션
        }
        System.out.println("세션 userId: " + userId); // 서버 IDE 터미널에 세션 값 출력
    {
      int pageSize = 5; // 한 페이지의 게시글 수
      PageRequest pageable = PageRequest.of(page, pageSize); // 한 페이지의 게시글 수
      Page<Board> list; // Page를 반환

      if (keyword.isEmpty()) {
        list = blogService.findAll(pageable); // 기본 전체 출력(키워드 x)
      } else {
        list = blogService.searchByKeyword(keyword, pageable); // 키워드로 검색
      }

      // 페이지 기준 시작 번호 계산
      int startNum = (page * pageSize) + 1;

      model.addAttribute("boards", list); // 모델에 추가
      model.addAttribute("totalPages", list.getTotalPages()); // 페이지 크기
      model.addAttribute("currentPage", page); // 페이지 번호
      model.addAttribute("keyword", keyword); // 키워드
      model.addAttribute("email", email); // 로그인 사용자(이메일)
      model.addAttribute("startNum", startNum); // 시작 번호

      return "board_list"; // .HTML 연결
    }
}
    @GetMapping("/board_view/{id}") // 게시판 링크 지정
    public String board_view(Model model, @PathVariable Long id) {
      Optional<Board> list = blogService.findById(id); // 선택한 게시판 글

      if (list.isPresent()) {
        model.addAttribute("boards", list.get()); // 존재할 경우 실제 Board 객체를 모델에 추가
      } else {
        // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
        return "/error_page/article_error"; // 오류 처리 페이지로 연결
      }
      return "board_view"; // .HTML 연결
    }

    @GetMapping("/board_edit/{id}") // 게시판 글 수정 화면 맵핑
    public String board_edit(Model model, @PathVariable Long id) {
      Optional<Board> list = blogService.findById(id); // 선택한 게시판 글

      if (list.isPresent()) {
        model.addAttribute("board", list.get()); // 존재하면 Board 객체를 모델에 추가
      } else {
        // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
        return "error_page/article_error"; // 오류 처리 페이지로 연결
      }
      return "board_edit"; // .HTML 연결
    }



    // @GetMapping("/article_edit/{id}") // 게시판 링크 지정
    // public String article_edit(Model model, @PathVariable Long id){
    //     Optional<Article> list = blogService.findById(id); // 선택한 게시판 글

    //     if (list.isPresent()) {
    //         model.addAttribute("article", list.get()); // 존재하면 Article 객체를 모델에 추가
    //     } else {
    //         // 처리할 로직 추가 (예: 오류 페이지로 리다이렉트, 예외 처리 등)
    //         return "error"; // 오류 처리 페이지로 연결
    //     }
    //     return "article_edit"; // .HTML 연결
    // }

    // 글쓰기 게시판
    @GetMapping("/board_write")
      public String board_write() {
      return "board_write";
    }

    @PostMapping("/api/boards") // 글쓰기 게시판 저장
      public String addboards(@ModelAttribute AddArticleRequest request) {
      blogService.save(request);
      return "redirect:/board_list"; // .HTML 연결
    }


    @PutMapping("/api/article_edit/{id}")
    public String updateArticle(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
      blogService.update(id, request);
      return "redirect:/article_list"; // 글 수정 이후 .html 연결
    }

    @PutMapping("/api/board_edit/{id}")
    public String updateBoard(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
      blogService.update(id, request);
      return "redirect:/board_list"; // 글 수정 이후 board_list.html 연결
    }

    @DeleteMapping("/api/article_delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
      blogService.delete(id);
      return "redirect:/article_list";
    }

    @DeleteMapping("/api/board_delete/{id}")
    public String deleteBoard(@PathVariable Long id) {
      blogService.deleteBoard(id);
      return "redirect:/board_list";
    }

    @PostMapping("/api/articles") // form의 action 경로와 동일하게 설정
    public String addArticleRedirect(@ModelAttribute AddArticleRequest request) {
        // 1. BlogService의 save 메서드를 호출하여 게시글 저장
        blogService.save(request); 

        // 2. 저장 후 "/article_list" 경로로 리다이렉트 (페이지 자동 이동)
        return "redirect:/article_list"; 
    }
}

