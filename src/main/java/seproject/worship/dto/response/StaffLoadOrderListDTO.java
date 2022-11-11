package seproject.worship.dto.response;

import lombok.Getter;
import lombok.Setter;
import seproject.worship.entity.OrderMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class StaffLoadOrderListDTO {

    Long orderID;
    List<Map> orderMenus = new ArrayList<>();
    String cid;

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
}
