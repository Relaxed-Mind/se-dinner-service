package seproject.worship.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Item {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemUrl;

    @OneToMany(mappedBy="item")
    private List<MenuItem> menuItems = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<ModifiedItem> modifiedItems = new ArrayList<>();

    private String name;
    private Integer price;
    private Integer stockQuantity;

    public void addQuantity(Integer addQuantity){
        this.stockQuantity+=addQuantity;
    }

    @Builder
    public Item(Long id, String itemUrl, String name, Integer price, Integer stockQuantity) {
        this.id = id;
        this.itemUrl = itemUrl;
        this.name = name;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }
}
