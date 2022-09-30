package seproject.worship.entity;

import lombok.Getter;
import seproject.worship.enumpack.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "orders")
@Entity
@Getter
public class Order extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<OrderMenu> orderMenu = new ArrayList<>();

    @OneToMany(mappedBy = "order")
    private List<ModifiedItem> modifiedItems = new ArrayList<>();

    private String destinationAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime reservationDate;
}
