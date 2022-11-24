package seproject.worship.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cid;
    private String pw;
    private String name;
    private String phoneNum;
    private String address;
    private String cardNum;

    @OneToMany(mappedBy = "customer")
    private List<CartMenu> cartMenus = new ArrayList<>();

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Customer(String cid, String pw, String name, String phoneNum, String address, String cardNum){
        this.cid = cid;
        this.pw = pw;
        this.name = name;
        this.phoneNum = phoneNum;
        this.address = address;
        this.cardNum = cardNum;
    }

    public void modifyInfo(String address, String cardNum, String phoneNum){
        this.address = address;
        this.cardNum = cardNum;
        this.phoneNum = phoneNum;
    }
}
