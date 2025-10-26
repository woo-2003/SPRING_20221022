package com.example.demo.model.service;

import org.springframework.stereotype.Service; // Autowired는 이제 필요 없으니 지워도 됩니다.
import com.example.demo.model.domain.TestDB;
import com.example.demo.model.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import java.util.List; // 1. List 임포트 추가

@Service // 서비스 등록
@RequiredArgsConstructor // 생성자 생성 (final 필드에 대해)
public class TestService {

    // 2. final 키워드 추가 (@Autowired 대신 사용)
    private final TestRepository testRepository;

    // 기존 findByName 메서드 (이름으로 한 명 찾기)
    public TestDB findByName(String name) {
        return testRepository.findByName(name); // (TestDB) 캐스팅은 보통 필요 없습니다.
    }

    // --- 3. 연습문제를 위한 새 메서드 추가 ---
    // 모든 사용자 목록을 반환
    public List<TestDB> findAllUsers() {
        return testRepository.findAll(); // JpaRepository의 기본 메서드 사용
    }
}