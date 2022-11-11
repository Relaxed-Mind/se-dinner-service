package seproject.worship.dto.request;

import lombok.Getter;

@Getter
public class OrderDTO {
    private Long customerId;
    private String reservationDate;
    private String destinationAddress;
    private String cardNum;
    private String phoneNum;
}
