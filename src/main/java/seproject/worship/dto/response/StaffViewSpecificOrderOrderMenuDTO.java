package seproject.worship.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import seproject.worship.enumpack.StyleStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class StaffViewSpecificOrderOrderMenuDTO {

    String menuName;
    Integer orderMenuPrice;
    StyleStatus styleStatus;
    Integer count;
    List<Map> modifiedItems = new ArrayList<>();

    @Builder
    public StaffViewSpecificOrderOrderMenuDTO(Integer orderMenuPrice, StyleStatus styleStatus, Integer count,String menuName) {
        this.menuName = menuName;
        this.orderMenuPrice = orderMenuPrice;
        this.styleStatus = styleStatus;
        this.count = count;
    }
}
