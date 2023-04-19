package ykh.ykhspring.buy;

import ykh.ykhspring.sell.Sell;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import ykh.ykhspring.user.SignUp;


import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Buy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    @ManyToOne
    private Sell sell;

    @ManyToOne
    private SignUp author;

    private LocalDateTime modifyDate;
}
