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
    private List<Item> items = new ArrayList<>();
}
