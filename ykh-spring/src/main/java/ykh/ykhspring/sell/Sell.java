package ykh.ykhspring.sell;

import lombok.Getter;
import lombok.Setter;
import ykh.ykhspring.buy.Buy;
import ykh.ykhspring.user.SignUp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Sell {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "sell", cascade = CascadeType.REMOVE)
    private List<Buy> buyList;

    private String oriImgName;

    private String imgName;

    private String imgPath;

    @NotNull
    private Integer price;

    @ManyToOne
    private SignUp author;

    private LocalDateTime modifyDate;
}
