package seproject.worship.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seproject.worship.enumpack.OrderStatus;
import seproject.worship.enumpack.StyleStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
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

    @Builder
    public OrderMenu(Long id, Menu menu, Order order, Integer orderMenuPrice, Integer count, StyleStatus styleStatus) {
        this.menu = menu;
        this.order = order;
        this.orderMenuPrice = orderMenuPrice;
        this.count = count;
        this.styleStatus = styleStatus;
    }

}
