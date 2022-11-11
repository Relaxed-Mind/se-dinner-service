package seproject.worship.dto.response;

import lombok.Getter;
import seproject.worship.enumpack.OrderStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
public class StaffLoadAcceptOrderListDTO {

    Long orderID;
    List<Map> orderMenus = new ArrayList<>();
    String cid;
    OrderStatus orderStatus;

    public void setOrderID(Long orderID) {
        this.orderID = orderID;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
