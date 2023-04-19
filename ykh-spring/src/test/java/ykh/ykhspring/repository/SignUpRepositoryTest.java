package ykh.ykhspring.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ykh.ykhspring.user.SignUp;
import ykh.ykhspring.user.SignUpRepository;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class SignUpRepositoryTest {

    @Autowired
    private SignUpRepository signUpRepository;

    @Test
    void testJpa(){
        SignUp user1 = new SignUp();
        user1.setUsername("user1");
        user1.setPassword("user1user1");
        user1.setEmail("user1@user1.com");
        this.signUpRepository.save(user1);

        SignUp user2 = new SignUp();
        user2.setUsername("user2");
        user2.setPassword("user2user2");
        user2.setEmail("user2@user2.com");
        this.signUpRepository.save(user2);
    }

    @Test
    void testJpa2(){
        List<SignUp> all = this.signUpRepository.findAll();
        Assertions.assertEquals(2 ,all.size());

        SignUp user = all.get(0); //유저 변수를 받을 때 SignUp을 사용해야한다.
        Assertions.assertEquals("user1", user.getUsername());
    }

    @Test
    void testJpa3(){
        Optional<SignUp> up = this.signUpRepository.findById(1L);//Optional 은 널값을 처리할 때 좋다.
        if(up.isPresent()){
            SignUp user = up.get();
            Assertions.assertEquals("user1", user.getUsername());
        }
    }

    @Test
    void testJpa4(){
        Optional<SignUp> up =this.signUpRepository.findByUsername("user1");
        if (up.isPresent()) {
            SignUp user = up.get();
            Assertions.assertEquals(1, user.getId());
        }
//        SignUp user = this.signUpRepository.findByUsername("user2");
//        Assertions.assertEquals("user2@user2.com", user.getEmail());
    }

    @Test
    void testJpa5(){
        SignUp user = this.signUpRepository.findByUsernameAndEmail(
                "user1", "user1@user1.com");
        Assertions.assertEquals(1, user.getId());
    }

    @Test
    void testJpa6(){
        List<SignUp> userlist = this.signUpRepository.findByEmailLike("%com");
        SignUp user = userlist.get(1);
        Assertions.assertEquals("user2@user2.com", user.getEmail());
    }

    @Test
    void testJpa7(){
        Optional<SignUp> user = this.signUpRepository.findById(2L);
        Assertions.assertTrue(user.isPresent());
        SignUp u = user.get();
        u.setEmail("lol@lol.com");
        this.signUpRepository.save(u);
    }

    @Test
    void testJpa8(){
        Assertions.assertEquals(2,this.signUpRepository.count());
        Optional<SignUp> user = this.signUpRepository.findById(2L);
        Assertions.assertTrue(user.isPresent());
        SignUp u = user.get();
        this.signUpRepository.delete(u); // findById(2L)에 해당하는 user2 데이터를 의미함
    }
}
