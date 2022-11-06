package seproject.worship.entity;

import lombok.Getter;
import seproject.worship.enumpack.StyleStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Menu {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private StyleStatus styleStatus;

    private String menuUrl;

    @OneToMany(mappedBy = "menu")
    private List<MenuItem> menuItems = new ArrayList<>();

    @OneToMany(mappedBy= "menu")
    private List<OrderMenu> orderMenus = new ArrayList<>();

    @OneToMany(mappedBy="menu")
    private List<CartMenu> cartMenus = new ArrayList<>();
}
