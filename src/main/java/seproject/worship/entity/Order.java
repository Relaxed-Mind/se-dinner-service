package seproject.worship.entity;

import seproject.worship.enumpack.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "orders")
@Entity
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<Menu> orderMenu = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<ModifiedItem> modifiedItems = new ArrayList<>();

    private String destinationAddress;
    private OrderStatus orderStatus;
    private LocalDateTime reservationDate;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;


}
