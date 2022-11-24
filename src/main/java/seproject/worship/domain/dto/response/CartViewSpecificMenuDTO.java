package seproject.worship.domain.dto.response;

import lombok.Getter;
import seproject.worship.domain.entity.CartMenu;
import seproject.worship.domain.entity.Item;
import seproject.worship.domain.entity.Menu;
import seproject.worship.domain.entity.ModifiedItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class CartViewSpecificMenuDTO {
    private String menuUrl;
    private String menuName;
    private Integer menuCount;
    private Integer cartMenuPrice;
    private String styleStatus;
    private List<Map> foods = new ArrayList<>();

    public CartViewSpecificMenuDTO(CartMenu cartMenu, List<ModifiedItem> modifiedItems){
        Menu menu = cartMenu.getMenu();
        this.menuUrl = menu.getMenuUrl();
        this.menuName = menu.getName();
        this.menuCount = cartMenu.getCount();
        this.cartMenuPrice = cartMenu.getCartMenuPrice();
        this.styleStatus = cartMenu.getStyleStatus().name();
        for (ModifiedItem modifiedItem : modifiedItems) {
            Map<String, Object> map = new HashMap<>();
            Item item = modifiedItem.getItem();
            Integer itemCount = modifiedItem.getCount();

            map.put("name", item.getName());
            map.put("price", item.getPrice() * itemCount);
            map.put("itemCount", itemCount);
            this.foods.add(map);
        }
    }

}
