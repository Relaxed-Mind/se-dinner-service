package seproject.worship.entity;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "cart", fetch = FetchType.LAZY)
    private Customer customer;

    @OneToMany(mappedBy = "cart")
    private List<CartMenu> cartMenus = new ArrayList<>();
}
