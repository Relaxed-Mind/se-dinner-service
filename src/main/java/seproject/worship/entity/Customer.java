package seproject.worship.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    private String id;
    private String pw;
    private String name;
    private String address;
    private String cardNum;

    @OneToMany(mappedBy = "customer")
    private List<Order> orders = new ArrayList<>();
}
