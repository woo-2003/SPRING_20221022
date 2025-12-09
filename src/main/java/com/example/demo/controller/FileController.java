
package com.example.demo.controller;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller // 컨트롤러 어노테이션 명시
public class FileController{
  @Autowired

  @Value("${spring.servlet.multipart.location}") // properties 등록된 설정(경로) 주입
  private String uploadFolder;

@PostMapping("/upload-email")
public String uploadEmail( // 이메일, 제목, 메시지를 전달받음
  @RequestParam("email") String email,
  @RequestParam("subject") String subject,
  @RequestParam("message") String message,
  RedirectAttributes redirectAttributes) {
    try {
      Path uploadPath = Paths.get(uploadFolder).toAbsolutePath();
      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
      }
      String sanitizedEmail = email.replaceAll("[^a-zA-Z0-9]", "_");
      String baseFileName = sanitizedEmail + ".txt";
      Path filePath = uploadPath.resolve(baseFileName);
      
      // 동일 파일명이 존재하면 타임스탬프와 UUID를 추가하여 다른 이름으로 저장
      if (Files.exists(filePath)) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String uuid = UUID.randomUUID().toString().substring(0, 8);
        String newFileName = sanitizedEmail + "_" + timestamp + "_" + uuid + ".txt";
        filePath = uploadPath.resolve(newFileName);
        System.out.println("동일 파일명 감지, 새 파일명: " + newFileName); // 디버깅용 출력
      }
      
      System.out.println("File path: " + filePath); // 디버깅용 출력

      try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
        writer.write("메일 제목: " + subject); // 쓰기
        writer.newLine(); // 줄 바꿈
        writer.write("요청 메시지:");
        writer.newLine();
        writer.write(message);
      }
    
      redirectAttributes.addFlashAttribute("message", "메일 내용이 성공적으로 업로드되었습니다!");
      return "upload_end"; // .html 파일 연동
      } catch (IOException e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("error", "파일 업로드 중 오류가 발생했습니다: " + e.getMessage());
        return "redirect:/error_page/file_upload_error"; // 파일 업로드 에러 페이지로 리다이렉트
      } catch (Exception e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("error", "예상치 못한 오류가 발생했습니다: " + e.getMessage());
        return "redirect:/error_page/file_upload_error"; // 파일 업로드 에러 페이지로 리다이렉트
      }
  }

  @GetMapping("/error_page/file_upload_error")
  public String fileUploadError(Model model) {
    return "error_page/file_upload_error"; // 파일 업로드 에러 페이지 렌더
  }
}


