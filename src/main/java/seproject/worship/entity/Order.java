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

    private String cardNum;
    private String phoneNum;

    @OneToMany(mappedBy = "order")
    private List<OrderMenu> orderMenus = new ArrayList<>();

    private String destinationAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime reservationDate;

    public void staffChangeOrderStatus(OrderStatus orderStatus){
        this.orderStatus=orderStatus;
    }

    public void customerCancelOrder(){
        this.orderStatus = OrderStatus.CANCELED;
    }

    @Builder
    public Order(Customer customer, String destinationAddress, OrderStatus orderStatus,
                 LocalDateTime reservationDate, String cardNum, String phoneNum) {
        this.customer = customer;
        this.destinationAddress = destinationAddress;
        this.orderStatus = orderStatus;
        this.reservationDate = reservationDate;
        this.cardNum = cardNum;
        this.phoneNum = phoneNum;
    }
}
