package ykh.ykhspring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;
import ykh.ykhspring.buy.Buy;
import ykh.ykhspring.buy.BuyForm;
import ykh.ykhspring.sell.Sell;
import ykh.ykhspring.service.BuyService;
import ykh.ykhspring.service.SellService;
import ykh.ykhspring.service.UserService;
import ykh.ykhspring.user.SignUp;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class BuyController {
    private final SellService sellService;
    private final BuyService buyService;
    private final UserService userService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/buy/{id}")
    public String buy(Model model, @PathVariable("id") Integer id,
                      @Valid BuyForm buyForm, BindingResult bindingResult, Principal principal) {
        Sell sell = this.sellService.getSell(id);
        SignUp signUp = this.userService.getUser(principal.getName());
        if (bindingResult.hasErrors()) {
            model.addAttribute("sell", sell);
            return "sell_detail";
        }
        this.buyService.create(sell, buyForm.getContent(), signUp);
        return String.format("redirect:/sell/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/buy/modify/{id}")
    public String buyModify(@Valid BuyForm buyForm, BindingResult bindingResult,
                            @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "buy_form";
        }
        Buy buy = this.buyService.getBuy(id);
        if (!buy.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "현재 사용자는 수정 권한이 없습니다.");
        }
        this.buyService.modify(buy, buyForm.getContent());
        return String.format("redirect:/sell/%s", buy.getSell().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/buy/delete/{id}")
    public String buyDelete(Principal principal, @PathVariable("id") Integer id) {
        Buy buy = this.buyService.getBuy(id);
        if (!buy.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.buyService.delete(buy);
        return String.format("redirect://question/detail/%s", buy.getSell().getId());
    }
}
