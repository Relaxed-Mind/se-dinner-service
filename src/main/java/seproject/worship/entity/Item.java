package seproject.worship.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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

}
