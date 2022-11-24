package seproject.worship.domain.dto.response;

import lombok.Getter;
import lombok.Setter;
import seproject.worship.domain.entity.Customer;

@Getter @Setter
public class CustomerLoadInfoDTO {
    private String cid;
    private String name;
    private String phoneNum;
    private String address;
    private String cardNum;

    public CustomerLoadInfoDTO(Customer customer){
        this.cid = customer.getCid();
        this.name = customer.getName();
        this.phoneNum = customer.getPhoneNum();
        this.address = customer.getAddress();
        this.cardNum = customer.getCardNum();
    }
}
