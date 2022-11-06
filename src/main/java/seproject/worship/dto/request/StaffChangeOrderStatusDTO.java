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

    Long id;
    OrderStatus orderStatus;

    @JsonCreator
    public StaffChangeOrderStatusDTO(@JsonProperty("id") Long id,
                                     @JsonProperty("orderStatus") OrderStatus orderStatus){
        this.id=id;
        this.orderStatus=orderStatus;
    }
}
