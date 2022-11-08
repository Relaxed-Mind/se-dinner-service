package seproject.worship.entity;

import lombok.Getter;
import seproject.worship.enumpack.StyleStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class OrderMenu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer orderMenuPrice;
    private Integer count;

    @Enumerated(EnumType.STRING)
    private StyleStatus styleStatus;

    @OneToMany(mappedBy = "orderMenu")
    private List<ModifiedItem> modifiedItems = new ArrayList<>();
}
