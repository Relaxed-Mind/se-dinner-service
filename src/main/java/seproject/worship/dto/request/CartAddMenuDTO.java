package seproject.worship.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class CartAddMenuDTO {
    private Long customerId;
    private Long menuId;
    private String style;
    private Integer count;
    private List<Map> foods = new ArrayList<>();
}
