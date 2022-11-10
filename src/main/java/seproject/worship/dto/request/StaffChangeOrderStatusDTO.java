package seproject.worship.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import seproject.worship.enumpack.OrderStatus;

@Getter @Setter
@ToString
public class StaffChangeOrderStatusDTO {

    Long orderId;
    OrderStatus orderStatus;

    @JsonCreator
    public StaffChangeOrderStatusDTO(@JsonProperty("orderId") Long orderId,
                                     @JsonProperty("orderStatus") OrderStatus orderStatus){
        this.orderId=orderId;
        this.orderStatus=orderStatus;
    }
}
