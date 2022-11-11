package seproject.worship.dto.response;

import lombok.Getter;
import seproject.worship.entity.Customer;

@Getter
public class beforeOrderDTO {
    private String address;
    private String phoneNum;
    private String cardNum;
    private Integer totalPrice;

    public beforeOrderDTO(Customer customer, Integer totalPrice){
        this.address = customer.getAddress();
        this.phoneNum = customer.getPhoneNum();
        this.cardNum = customer.getCardNum();
        this.totalPrice = totalPrice;
    }
}
