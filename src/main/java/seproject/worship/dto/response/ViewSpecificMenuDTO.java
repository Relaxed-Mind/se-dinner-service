package seproject.worship.dto.response;

import lombok.Getter;
import lombok.Setter;
import seproject.worship.entity.Menu;

@Getter @Setter
public class ViewSpecificMenuDTO {
    private String menuUrl;
    private Integer menuPrice;
    private String menuName;

    public ViewSpecificMenuDTO(Menu menu){
        this.menuPrice = menu.getMenuPrice();
        this.menuUrl = menu.getMenuUrl();
        this.menuName = menu.getName();
    }
}
