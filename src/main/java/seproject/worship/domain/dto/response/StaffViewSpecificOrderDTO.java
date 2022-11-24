package seproject.worship.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class StaffViewSpecificOrderDTO {

    Long orderId;
    List<StaffViewSpecificOrderOrderMenuDTO> orderMenus = new ArrayList<>();

}
