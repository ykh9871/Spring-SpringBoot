package ykh.ykhspring.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ykh.ykhspring.sell.Sell;
import ykh.ykhspring.sell.SellRepository;
import ykh.ykhspring.user.DataNotFoundException;
import ykh.ykhspring.user.SignUp;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellService {
    private final SellRepository sellRepository;

    public Page<Sell> getList(int page) {
        Sort sort = Sort.by("createDate").descending();
        Pageable pageable = PageRequest.of(page, 9, sort);
        return this.sellRepository.findAll(pageable); // 페이징 기법
    }
//    JPA 를 활용한 정렬
//    • findAll()에는 Sort를 지원
//    • SellService에서 sortDateList()를 생성
//    • “createDate” 를 기준으로 내림차순 정렬
//    public List<Sell> sortDateList() {
//        return this.sellRepository.findAll(Sort.by(Sort.Direction.DESC, "createDate"));
//    }
    public Sell getSell(Integer id) {
        Optional<Sell> sell = this.sellRepository.findById(id);
        if (sell.isPresent()) {
            return sell.get();
        }else {
            throw new DataNotFoundException("sell is not found");
        }
    }
    public void create(String subject, String content, Integer price, SignUp user, MultipartFile upload) throws IOException {
        Sell s = new Sell();
        s.setSubject(subject);
        s.setContent(content);
        s.setCreateDate(LocalDateTime.now());
        s.setAuthor(user);
        s.setPrice(price);

        String originalImgName = upload.getOriginalFilename();
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/images/";

        UUID uuid = UUID.randomUUID();
        String imgName = uuid + "_" + originalImgName;

        if (imgName.contains(".")) {
            File saveFile = new File(projectPath, imgName);

            upload.transferTo(saveFile);

            s.setImgName(imgName);
            s.setImgPath("/images/" + imgName);

            this.sellRepository.save(s);
        } else {
            this.sellRepository.save(s);
        }
    }
    public void modify(Sell sell, String subject, String content, Integer price, MultipartFile upload) throws IOException {
        sell.setSubject(subject);
        sell.setContent(content);
        sell.setModifyDate(LocalDateTime.now());
        sell.setPrice(price);

        String originalImgName = upload.getOriginalFilename();
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/images/";

        UUID uuid = UUID.randomUUID();
        String imgName = uuid + "_" + originalImgName;

        if (imgName.contains(".")) {
            File saveFile = new File(projectPath, imgName);

            upload.transferTo(saveFile);

            sell.setOriImgName(originalImgName);
            sell.setImgName(imgName);
            sell.setImgPath("/images/" + imgName);

            this.sellRepository.save(sell);
        } else {
            this.sellRepository.save(sell);
        }
    }

    public void delete(Sell sell) {
        this.sellRepository.delete(sell);
    }
}
