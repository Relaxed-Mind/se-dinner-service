package seproject.worship.domain.dto.response;

import lombok.Getter;
import seproject.worship.domain.entity.Customer;

@Getter
public class beforeOrderDTO {
    private String address;
    private String phoneNum;
    private String cardNum;
    private Integer totalPrice;
    private Integer discountPrice;

    public beforeOrderDTO(Customer customer, Integer totalPrice, Integer discountPrice){
        this.address = customer.getAddress();
        this.phoneNum = customer.getPhoneNum();
        this.cardNum = customer.getCardNum();
        this.totalPrice = totalPrice;
        this.discountPrice = discountPrice;
    }
}
