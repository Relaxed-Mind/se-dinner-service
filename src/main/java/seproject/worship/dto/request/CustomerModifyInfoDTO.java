package seproject.worship.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerModifyInfoDTO {
    private Long customerId;
    private String address;
    private String cardNum;
    private String phoneNum;
}
