package seproject.worship.domain.dto.request;

import lombok.Getter;

@Getter
public class OrderDTO {
    private Long customerId;
    private String reservationDate;
    private String destinationAddress;
    private String cardNum;
    private String phoneNum;
}
