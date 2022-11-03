package seproject.worship.entity;

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
    private Long customerId;

    private String id;
    private String pw;
    private String name;
    private String phoneNum;
    private String address;
    private String cardNum;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Cart cart;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Customer(String id, String pw, String name, String phoneNum, String address, String cardNum){
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.phoneNum = phoneNum;
        this.address = address;
        this.cardNum = cardNum;
    }
}
