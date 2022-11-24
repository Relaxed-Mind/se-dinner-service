package seproject.worship.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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

    public void useQuantity(Integer useQuantity){

        if(this.stockQuantity<useQuantity){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"item 수량이 부족하여 Order 수락 불가능");}
        else{
            this.stockQuantity-=useQuantity;}
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
