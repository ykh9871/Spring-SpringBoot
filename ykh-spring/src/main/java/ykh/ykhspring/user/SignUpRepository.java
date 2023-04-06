package ykh.ykhspring.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 테이블에 데이터를 CRUD(Create(insert), Read(select), Update, Delete) 연산하는 로직 역할
public interface SignUpRepository extends JpaRepository<SignUp, Long> {
    SignUp findByUsername(String username);

    SignUp findByUsernameAndEmail(String username, String email);

    List<SignUp> findByEmailLike(String email);
}
