package com.example.demo.exception; // 패키지 경로 확인

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
// import org.springframework.ui.Model; // 필요 시 Model 추가 가능

@ControllerAdvice // 이 클래스가 전역 예외 처리를 담당함을 선언
public class GlobalExceptionHandler {

    // MethodArgumentTypeMismatchException 예외가 발생하면 이 메서드가 처리
    @ExceptionHandler(MethodArgumentTypeMismatchException.class) 
    public String handleTypeMismatchException(MethodArgumentTypeMismatchException ex /*, Model model*/) {
        // 콘솔에 에러 로그 출력 (선택 사항)
        System.err.println("잘못된 타입의 파라미터 요청: " + ex.getMessage()); 

        // model.addAttribute("errorMessage", "ID는 숫자 형식이어야 합니다."); // 에러 메시지를 모델에 담아 전달 가능

        // 아까 만든 커스텀 에러 페이지 경로 반환
        return "error_page/error_type_mismatch"; 
    }

    /*
    @ExceptionHandler(Exception.class) // 모든 종류의 예외 처리
    public String handleGenericException(Exception ex, Model model) {
        System.err.println("알 수 없는 에러 발생: " + ex.getMessage());
        model.addAttribute("errorMessage", "알 수 없는 오류가 발생했습니다.");
        return "error_page/generic_error"; // 일반 에러 페이지
    }
    */
}