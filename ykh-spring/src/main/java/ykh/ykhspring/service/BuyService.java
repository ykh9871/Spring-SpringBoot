package ykh.ykhspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ykh.ykhspring.buy.Buy;
import ykh.ykhspring.buy.BuyRepository;
import ykh.ykhspring.sell.Sell;
import ykh.ykhspring.user.DataNotFoundException;
import ykh.ykhspring.user.SignUp;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BuyService {
    private final BuyRepository buyRepository;

    public void create(Sell sell, String content, SignUp author) {
        Buy buy = new Buy();
        buy.setContent(content);
        buy.setCreateDate(LocalDateTime.now());
        buy.setSell(sell);
        buy.setAuthor(author);
        this.buyRepository.save(buy);
    }
    public Buy getBuy(Integer id) {
        Optional<Buy> answer = this.buyRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

    public void modify(Buy buy, String content) {
        buy.setContent(content);
        buy.setModifyDate(LocalDateTime.now());
        this.buyRepository.save(buy);
    }

    public void delete(Buy buy) {
        this.buyRepository.delete(buy);
    }
}
