package seproject.worship.dto.response;

import lombok.Getter;
import lombok.Setter;
import seproject.worship.entity.CartMenu;
import seproject.worship.entity.Menu;
import seproject.worship.enumpack.StyleStatus;

import java.util.HashMap;
import java.util.Map;

@Getter @Setter
public class CartLoadMenuListDTO {
    private String menuName;
    private String menuURL;
    private Integer count;
    private Integer cartMenuPrice;
    private String styleStatus;

    public CartLoadMenuListDTO(Menu menu, CartMenu cartMenu){
        this.menuName = menu.getName();
        this.menuURL = menu.getMenuUrl();
        this.count = cartMenu.getCount();
        this.cartMenuPrice = cartMenu.getCartMenuPrice();
        this.styleStatus = cartMenu.getStyleStatus().name();
    }

    public Map<String, Object> CartLoadMenuListDTOtoMap(){
        Map<String, Object> map = new HashMap<>();
        map.put("menuName", this.menuName);
        map.put("menuURL", this.menuURL);
        map.put("count", this.count);
        map.put("cartMenuPrice", this.cartMenuPrice);
        map.put("styleStatus", this.styleStatus);

        return map;
    }


}
