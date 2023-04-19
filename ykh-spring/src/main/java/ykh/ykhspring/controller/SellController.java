package ykh.ykhspring.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import ykh.ykhspring.buy.BuyForm;
import ykh.ykhspring.sell.Sell;
import ykh.ykhspring.sell.SellForm;
import ykh.ykhspring.sell.SellRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ykh.ykhspring.service.SellService;
import ykh.ykhspring.service.UserService;
import ykh.ykhspring.user.SignUp;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class SellController {

    private final SellService sellService;
    private final UserService userService;

    @GetMapping("/sell")
    public String sell(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Sell> sellList = this.sellService.getList(page);
//        List<Sell> sellList = this.sellService.getList(page);// sortDateList() JPA를 활용한 정렬에서 기존 SellController에서 sellList를 받아올 때 getList()를 위에서 생성한 sortDateList()로 변경
//        sellList를 sortedSellList로 재정렬
//        Stream API를 활용
//        List<Sell> sortedSellList = sellList
//                .stream()
//                .sorted(Comparator.comparing(Sell::getCreateDate).reversed())
//                .collect(Collectors.toList());
        model.addAttribute("sellList", sellList);
        return "sell_list";
    }

    @GetMapping(value = "/sell/{id}")
    public String detail(Model model, @PathVariable("id") Integer id, BuyForm buyForm) {
        Sell sell = this.sellService.getSell(id);
        model.addAttribute("sell", sell);
        return "sell_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/sell/create")
    public String sellCreate(SellForm sellForm) {
        return "sell_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/sell/create")
    public String sellCreate(@Valid SellForm sellForm, BindingResult bindingResult ,
                             Principal principal, MultipartFile upload) throws IOException {
        if (bindingResult.hasErrors()) {
            return "sell_form";
        }
        SignUp signUp = this.userService.getUser(principal.getName());
        this.sellService.create(sellForm.getSubject(), sellForm.getContent(), sellForm.getPrice(), signUp, upload);
        return "redirect:/sell/list";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/sell/modify/{id}")
    public String sellmodify(Model model, SellForm sellForm, @PathVariable("id") Integer id, Principal principal) {
        Sell sell = this.sellService.getSell(id);
        if (!sell.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없는 사용자 입니다.");
        } else {
            sellForm.setSubject(sell.getSubject());
            sellForm.setContent(sell.getContent());
            sellForm.setPrice(sell.getPrice());
            String originalFilName = sell.getOriImgName();
            model.addAttribute("filename", originalFilName);
            return "sell_form";
        }
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/sell/modify/{id}")
    public String sellModify(@Valid SellForm sellForm, BindingResult bindingResult,
                             @PathVariable("id") Integer id, Principal principal, MultipartFile upload) throws IOException {
        if (bindingResult.hasErrors()) {
            return "sell_form";
        }

        Sell sell = this.sellService.getSell(id);
        if (!sell.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정 권한이 없는 사용자입니다.");
        } else {
            this.sellService.modify(sell, sellForm.getSubject(), sellForm.getContent(), sellForm.getPrice(), upload);
            return String.format("redirect:/sell/%s", id);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/sell/delete/{id}")
    public String sellDelete(Principal principal, @PathVariable("id") Integer id) {
        Sell sell = this.sellService.getSell(id);
        if (!sell.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없는 사용자입니다.");
        } else {
            this.sellService.delete(sell);
            return "redirect:/";
        }
    }
}
