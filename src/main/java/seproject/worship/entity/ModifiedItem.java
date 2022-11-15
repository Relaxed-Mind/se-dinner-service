package seproject.worship.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ModifiedItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_menu_id")
    private OrderMenu orderMenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_menu_id")
    private CartMenu cartMenu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer count;

    @Builder
    public ModifiedItem(Item item, Integer count){
        this.item = item;
        this.count = count;
    }

    public void setCartMenu(CartMenu cartMenu){
        if(this.cartMenu != null){
            this.cartMenu.getModifiedItems().remove(this);
        } else{
            this.cartMenu = cartMenu;
            if(!cartMenu.getModifiedItems().contains(this)){
                cartMenu.getModifiedItems().add(this);
            }
        }
    }

    public void setOrderMenu(OrderMenu orderMenu){
        if(this.orderMenu != null)
            this.orderMenu.getModifiedItems().remove(this);
        else{
            this.orderMenu = orderMenu;
            if(!orderMenu.getModifiedItems().contains(this))
                orderMenu.getModifiedItems().add(this);
        }
    }

}
