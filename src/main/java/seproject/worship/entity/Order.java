package seproject.worship.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import seproject.worship.config.BaseTimeEntity;
import seproject.worship.enumpack.OrderStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "orders")
@Entity
@Getter
@NoArgsConstructor
public class Order extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<OrderMenu> orderMenus = new ArrayList<>();

    private String destinationAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime reservationDate;

    public void staffChangeOrderStatus(OrderStatus orderStatus){
        this.orderStatus=orderStatus;
    }

    @Builder
    public Order(Long id, Customer customer, String destinationAddress, OrderStatus orderStatus, LocalDateTime reservationDate) {
        this.id = id;
        this.customer = customer;
        this.orderMenus = orderMenus;
        this.destinationAddress = destinationAddress;
        this.orderStatus = orderStatus;
        this.reservationDate = reservationDate;
    }
}
