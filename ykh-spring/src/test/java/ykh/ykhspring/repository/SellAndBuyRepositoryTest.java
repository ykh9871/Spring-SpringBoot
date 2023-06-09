package ykh.ykhspring.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ykh.ykhspring.buy.Buy;
import ykh.ykhspring.buy.BuyRepository;
import ykh.ykhspring.sell.Sell;
import ykh.ykhspring.sell.SellRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SellAndBuyRepositoryTest {


    @Autowired
    private SellRepository sellRepository;

    @Autowired
    private BuyRepository buyRepository;

    @Test
    void testJpa_1() {
        Sell s1 = new Sell();
        s1.setSubject("자전거 판매 합니다.");
        s1.setContent("자전거 급매로 내놓습니다.");
        s1.setCreateDate(LocalDateTime.now());
        this.sellRepository.save(s1);

        Sell s2 = new Sell();
        s2.setSubject("갤럭시 s22 판매 합니다.");
        s2.setContent("갤럭시 s22 급매로 내놓습니다. 얼른 구입하세요!");
        s2.setCreateDate(LocalDateTime.now());
        this.sellRepository.save(s2);
    }

    @Test
    void testJpa_2() {
        List<Sell> all = this.sellRepository.findAll();
        assertEquals(2, all.size());

        Sell s = all.get(0);
        assertEquals("자전거 판매 합니다.", s.getSubject());
    }

    @Test
    void testJpa_3() {
        Optional<Sell> os = this.sellRepository.findById(1);
        if (os.isPresent()) {
            Sell s = os.get();
            assertEquals("자전거 급매로 내놓습니다.", s.getContent());
        }
    }

    @Test
    void testJpa_4() {
        Sell s = this.sellRepository.findBySubject("갤럭시 s22 판매 합니다.");
        assertEquals(2, s.getId());
    }

    @Test
    void testJpa_5() {
        Optional<Sell> os = this.sellRepository.findById(1);
        assertTrue(os.isPresent());
        Sell s = os.get();
        s.setSubject("자전거 초초초초초초 급매 판매합니다. 가격다운!!");
        this.sellRepository.save(s);
    }

    @Test
    void testJpa_6() {
        assertEquals(2, this.sellRepository.count());
        Optional<Sell> os = this.sellRepository.findById(1);
        assertTrue(os.isPresent());
        Sell s = os.get();
        this.sellRepository.delete(s);
        assertEquals(1, this.sellRepository.count());
    }

    @Test
    void testJpa_7() {
        Optional<Sell> os = this.sellRepository.findById(2);
        assertTrue(os.isPresent());
        Sell s = os.get();

        Buy b = new Buy();
        b.setContent("10만원도 비싼데, 제가 5만원에 살께요");
        b.setSell(s);
        b.setCreateDate(LocalDateTime.now());
        this.buyRepository.save(b);
    }

    @Test
    void testJpa_8() {
        Optional<Buy> ob = this.buyRepository.findById(1);
        assertTrue(ob.isPresent());
        Buy b = ob.get();
        assertEquals(2, b.getSell().getId());
    }

    @Test
    void testJpa_9() {
        for (int i = 1; i <= 100; i++) {
            Sell s = new Sell();
            s.setSubject(String.format("테스트 판매글 입니다 [%03d]", i));
            s.setContent("내용은 없어요");
            s.setCreateDate(LocalDateTime.now());
            s.setPrice(5000);
            this.sellRepository.save(s);
        }
    }

    @Test
    void testJpa_10() {
        for (int i = 101; i <= 200; i++) {
            Sell s = new Sell();
            s.setSubject(String.format("테스트 판매글 입니다 [%03d]", i));
            s.setContent("내용은 없어요");
            s.setCreateDate(LocalDateTime.now());
            s.setPrice(5000);
            this.sellRepository.save(s);
        }
    }
}
