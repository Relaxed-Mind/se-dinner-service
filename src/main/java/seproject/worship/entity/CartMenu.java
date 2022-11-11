package seproject.worship.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seproject.worship.enumpack.StyleStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class CartMenu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @OneToMany(mappedBy = "cartMenu")
    private List<ModifiedItem> modifiedItems = new ArrayList<>();

    private Integer cartMenuPrice;

    private Integer count;

    @Enumerated(EnumType.STRING)
    private StyleStatus styleStatus;

    @Builder
    public CartMenu(Customer customer, Menu menu, Integer cartMenuPrice, Integer count, StyleStatus styleStatus){
        this.customer = customer;
        this.menu = menu;
        this.cartMenuPrice = cartMenuPrice;
        this.count = count;
        this.styleStatus = styleStatus;
    }
}
