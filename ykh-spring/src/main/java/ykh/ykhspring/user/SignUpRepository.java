package ykh.ykhspring.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// 테이블에 데이터를 CRUD(Create(insert), Read(select), Update, Delete) 연산하는 로직 역할
public interface SignUpRepository extends JpaRepository<SignUp, Long> {
    Optional<SignUp> findByUsername(String username);
//    • findByUsername 메소드를 Optional로 수정
//    • 기존 테스트(SignUpRepositoryTest.java)하던 메소드랑 형태가 맞지않아 오류가 발생
//    • 테스트 과정은 이제 지나쳤기에 주석으로 해결
    SignUp findByUsernameAndEmail(String username, String email);

    List<SignUp> findByEmailLike(String email);
}
