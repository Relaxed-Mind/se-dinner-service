package seproject.worship.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CustomerSaveDTO {
    private String cid;
    private String pw;
    private String name;
    private String address;
    private String cardNum;
    private String phoneNum;
}
