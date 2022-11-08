package seproject.worship.entity;

import lombok.Getter;
import seproject.worship.enumpack.StyleStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
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

}
