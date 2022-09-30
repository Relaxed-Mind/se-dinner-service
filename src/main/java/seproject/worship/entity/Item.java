package seproject.worship.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private String name;
    private Integer price;
    private Integer stockQuantity;

}
