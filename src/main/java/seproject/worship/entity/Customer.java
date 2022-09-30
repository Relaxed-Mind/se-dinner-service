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
public class Customer extends BaseTimeEntity{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String id;
    private String pw;
    private String name;
    private String address;
    private String cardNum;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Customer(String id, String pw, String name, String address, String cardNum){
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.address = address;
        this.cardNum = cardNum;
    }
}
