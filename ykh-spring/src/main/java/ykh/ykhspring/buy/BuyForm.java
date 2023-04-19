package ykh.ykhspring.buy;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BuyForm {
    @NotEmpty(message = "내용은 필수항목으로 입력하셔야 합니다.")
    private String content;
}
