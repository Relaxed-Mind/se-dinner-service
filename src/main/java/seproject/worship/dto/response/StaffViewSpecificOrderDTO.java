package seproject.worship.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import seproject.worship.entity.OrderMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter @Setter
public class StaffViewSpecificOrderDTO {

    Long orderId;
    List<StaffViewSpecificOrderOrderMenuDTO> orderMenus = new ArrayList<>();

}
