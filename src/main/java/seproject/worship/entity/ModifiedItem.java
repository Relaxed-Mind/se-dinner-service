package seproject.worship.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ModifiedItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private char isAdd;
    private Integer count;
}
