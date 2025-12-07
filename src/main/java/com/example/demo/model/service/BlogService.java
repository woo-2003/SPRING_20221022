package com.example.demo.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.model.domain.Article;
import com.example.demo.model.domain.Board;
import com.example.demo.model.repository.BlogRepository;
import com.example.demo.model.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor // 생성자 자동 생성(부분)
public class BlogService {
  @Autowired // 객체 주입 자동화, 생성자 1개면 생략 가능
  private final BlogRepository blogRepository; // 리포지토리 선언
  private final BoardRepository blogRepository2; // 리포지토리 선언

  // public List<Article> findAll() { // 게시판 전체 목록 조회
  //   return blogRepository.findAll();
  // }

  public List<Board> findAll() { // 게시판 전체 목록 조회
    return blogRepository2.findAll();
  }


  // public Article save(AddArticleRequest request){
  //   // DTO가 없는 경우 이곳에 직접 구현 가능
  //   // public ResponseEntity<Article> addArticle(@RequestParam String title, @RequestParam String content) {
  //   // Article article = Article.builder()
  //   // .title(title)
  //   // .content(content)
  //   // .build();  
  //   return blogRepository.save(request.toEntity());
  // }

  public Board save(AddArticleRequest request){
    // DTO가 없는 경우 이곳에 직접 구현 가능
    return blogRepository2.save(request.toEntity());
  }

  // public Optional<Article> findById(Long id) { // 게시판 특정 글 조회
  //   return blogRepository.findById(id);
  // }

  public Optional<Board> findById(Long id) { // 게시판 특정 글 조회
    return blogRepository2.findById(id);
  }

  
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

  public void delete(Long id) {
    blogRepository.deleteById(id);
  }

  public Page<Board> findAll(Pageable pageable) {
    return blogRepository2.findAll(pageable);
  }
    public Page<Board> searchByKeyword(String keyword, Pageable pageable) {
    return blogRepository2.findByTitleContainingIgnoreCase(keyword, pageable);
  } // LIKE 검색 제공(대소문자 무시)

}