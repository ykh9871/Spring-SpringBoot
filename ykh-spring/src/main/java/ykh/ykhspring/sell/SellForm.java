package ykh.ykhspring.sell;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SellForm {
    @NotEmpty(message = "제목은 필수항목 입니다.")
    @Size(max = 200)
    private String subject;

    @NotEmpty(message = "내용은 필수항목 입니다.")
    private String content;

    @NotNull(message = "가격은 필수항목 입니다.")
    private Integer price;
}
