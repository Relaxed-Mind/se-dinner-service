package seproject.worship.domain.dto.request;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class CartModifyMenuDTO {

    private Long customerId;
    private Long cartMenuId;
    private Long menuId;
    private String style;
    private Integer count;
    private List<Map> foods = new ArrayList<>();

}
